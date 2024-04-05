package com.nchl.moneytracker.presentation.otp

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.nchl.moneytracker.data.api.ApiClient
import com.nchl.moneytracker.data.api.ApiConstants
import com.nchl.moneytracker.data.api.ApiInterface
import com.nchl.moneytracker.data.model.otp.OtpRequest
import com.nchl.moneytracker.data.model.otp.OtpResponse
import com.nchl.moneytracker.presentation.utils.exception.TrackerError
import com.nchl.moneytracker.presentation.utils.exception.TrackerException
import com.nchl.moneytracker.presentation.utils.log.Logger
import global.citytech.easydroid.core.mvvm.BaseAndroidViewModel
import global.citytech.easydroid.core.utils.Jsons
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpViewModel(private val context: Application) : BaseAndroidViewModel(context) {

    private val TAG = Logger(OtpViewModel::class.java.name).toString()
    var otp = MutableLiveData<String>()

    fun addOtpString(buttonText: String) {
        val originalOtpString = otp.value
        otp.value = originalOtpString.plus(buttonText)
        println("OTP ::: " + otp.value)
    }

    fun removeOtpString() {
        val originalOtpString = otp.value
        otp.value = originalOtpString?.substring(0, originalOtpString.length - 1)
        println("OTP ::: " + otp.value)
    }

    fun submitOtp(otpValue: String?) {
        isLoading.value = true
        compositeDisposable.add(
            Observable.create(ObservableOnSubscribe<OtpResponse> {
                onSubscribeOtp(it, OtpRequest("", otpValue!!))
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext, this::onError)
        )
    }


    private fun onSubscribeOtp(
        emitter: ObservableEmitter<OtpResponse>,
        otpRequest: OtpRequest
    ) {
        val retrofitService = ApiClient().getRetrofit(ApiConstants.BASE_URL)
            .create(ApiInterface::class.java)
        retrofitService.verifyOtp(otpRequest).enqueue(object :
            Callback<Any> {
            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                if (response.isSuccessful) {
                    val otpResponse: com.nchl.moneytracker.data.model.Response =
                        Jsons.fromJsonToObj(
                            response.body().toString(),
                            com.nchl.moneytracker.data.model.Response::class.java
                        )
                    if (otpResponse.data != null) {
                        prepareLoginResponse(emitter, otpResponse.data as Any)
                    } else {
                        if (otpResponse.code == "401")
                            emitter.onError(TrackerException(TrackerError.OTP_VERIFICATION_FAILED))
                        else
                            emitter.onError(Exception("Error Code :: ${otpResponse.code}. ${otpResponse.message}"))
                    }
                } else {
                    //emitter.onError(TrackerException(TrackerError.OTP_VERIFICATION_FAILED))
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                emitter.onError(TrackerException(TrackerError.USER_LOGIN_FAILED))
            }
        })

        emitter.onComplete()
    }

    private fun prepareLoginResponse(emitter: ObservableEmitter<OtpResponse>, data: Any) {
        val responseJson = Jsons.toJsonObj(data)
        val response = Jsons.fromJsonToObj(responseJson, OtpResponse::class.java)
        emitter.onNext(response)
    }

    private fun onNext(otpResponse: OtpResponse) {
        isLoading.value = false
    }


    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        isLoading.value = false
        if (throwable is TrackerException) {
            Logger.getLogger(TAG).debug(throwable.trackerError.errorMessage)
            val tackerException = throwable.trackerError
            if (tackerException == TrackerError.OTP_VERIFICATION_FAILED)
                message.value = tackerException.errorMessage
            else
                message.value = tackerException.errorMessage
        } else {
            throwable.message?.let { Logger.getLogger(TAG).debug(it) }
            message.value = throwable.message
        }
    }
}