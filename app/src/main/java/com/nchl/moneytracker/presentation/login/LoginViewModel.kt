package com.nchl.moneytracker.presentation.login

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.nchl.moneytracker.data.api.ApiClient
import com.nchl.moneytracker.data.api.ApiConstants
import com.nchl.moneytracker.data.api.ApiInterface
import com.nchl.moneytracker.data.model.login.LoginRequest
import com.nchl.moneytracker.data.model.login.LoginResponse
import com.nchl.moneytracker.presentation.utils.AppUtility
import com.nchl.moneytracker.presentation.utils.exception.TrackerError
import com.nchl.moneytracker.presentation.utils.exception.TrackerException
import com.nchl.moneytracker.presentation.utils.log.Logger
import global.citytech.easydroid.core.extension.showToast
import global.citytech.easydroid.core.mvvm.BaseAndroidViewModel
import global.citytech.easydroid.core.utils.Jsons
import global.citytech.finpos.merchant.extensions.hasInternetConnection
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@RequiresApi(Build.VERSION_CODES.O)
class LoginViewModel(private val context: Application) : BaseAndroidViewModel(context) {

    private val TAG = Logger(LoginViewModel::class.java.name).toString()
    val loginProcessStart by lazy { MutableLiveData<Boolean>() }

    fun validateLoginCredential(phoneNumber: String, password: String) {
        if (phoneNumber.isNullOrEmpty() && password.isNullOrEmpty()) {
            context.showToast("please fill in all the details", Toast.LENGTH_SHORT)
        } else if (AppUtility.isValidPhoneNumber(context, phoneNumber)) {
            if (password.isNullOrEmpty()) {
                context.showToast("password cannot be empty", Toast.LENGTH_SHORT)
            } else if (!context.hasInternetConnection()) {
                context.showToast("No internet connection", Toast.LENGTH_SHORT)
            } else {
                loginProcessStart.value = true
            }
        }
    }

    fun login(email: String, password: String) {
        isLoading.value = true
        compositeDisposable.add(
            Observable.create(ObservableOnSubscribe<LoginResponse> {
                onSubscribeLogin(it, LoginRequest(email, password))
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext, this::onError)
        )
    }


    private fun onSubscribeLogin(
        emitter: ObservableEmitter<LoginResponse>,
        loginRequest: LoginRequest
    ) {
        val retrofitService = ApiClient().getRetrofit(ApiConstants.BASE_URL)
            .create(ApiInterface::class.java)
        retrofitService.login(loginRequest).enqueue(object :
            Callback<Any> {
            override fun onResponse(
                call: Call<Any>,
                response: Response<Any>
            ) {
                if (response.isSuccessful) {
                    val loginResponse: com.nchl.moneytracker.data.model.Response =
                        Jsons.fromJsonToObj(
                            response.body().toString(),
                            com.nchl.moneytracker.data.model.Response::class.java
                        )
                    if (loginResponse.data != null) {
                        prepareLoginResponse(emitter, loginResponse.data as Any)
                    } else {
                        if (loginResponse.code == "401")
                            emitter.onError(TrackerException(TrackerError.USER_LOGIN_FAILED))
                        else
                            emitter.onError(Exception("Error Code :: ${loginResponse.code}. ${loginResponse.message}"))
                    }
                } else {
                    //emitter.onError(TrackerException(TrackerError.USER_LOGIN_FAILED))
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                emitter.onError(TrackerException(TrackerError.USER_LOGIN_FAILED))
            }
        })

        emitter.onComplete()
    }

    private fun prepareLoginResponse(emitter: ObservableEmitter<LoginResponse>, data: Any) {
        val responseJson = Jsons.toJsonObj(data)
        val response = Jsons.fromJsonToObj(responseJson, LoginResponse::class.java)
        emitter.onNext(response)
    }

    private fun onNext(loginResponse: LoginResponse) {
        isLoading.value = false
    }


    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        isLoading.value = false
        if (throwable is TrackerException) {
            Logger.getLogger(TAG).debug(throwable.trackerError.errorMessage)
            val tackerException = throwable.trackerError
            if (tackerException == TrackerError.USER_LOGIN_FAILED)
                message.value = tackerException.errorMessage
            else
                message.value = tackerException.errorMessage
        } else {
            throwable.message?.let { Logger.getLogger(TAG).debug(it) }
            message.value = throwable.message
        }
    }
}