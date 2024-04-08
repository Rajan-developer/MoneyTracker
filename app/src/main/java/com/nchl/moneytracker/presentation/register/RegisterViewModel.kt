package com.nchl.moneytracker.presentation.register

import android.app.Application
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.nchl.moneytracker.data.api.ApiClient
import com.nchl.moneytracker.data.api.ApiConstants
import com.nchl.moneytracker.data.api.ApiInterface
import com.nchl.moneytracker.data.model.register.RegisterRequest
import com.nchl.moneytracker.data.model.register.RegisterResponse
import com.nchl.moneytracker.presentation.otp.sendOtpByEmail
import com.nchl.moneytracker.presentation.utils.AppUtility.generateRandomDigits
import com.nchl.moneytracker.presentation.utils.AppUtility.isValidEmail
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
class RegisterViewModel(private val context: Application) : BaseAndroidViewModel(context) {

    private val TAG = Logger(RegisterViewModel::class.java.name).toString()
    val registerProcessStart by lazy { MutableLiveData<Boolean>() }


    fun validateRegisterCredential(
        username: String,
        email: String,
        password: String,
        confirmPassword: String
    ) {
//        if (username.isNullOrEmpty() && email.isNullOrEmpty() && password.isNullOrEmpty() && confirmPassword.isNullOrEmpty()) {
//            context.showToast("please fill in all the details", Toast.LENGTH_SHORT)
//        } else if (username.isNullOrEmpty()) {
//            context.showToast("username cannot be empty", Toast.LENGTH_SHORT)
//        } else if (isValidEmail(context,email)) {
//            if (password.isNullOrEmpty()) {
//                context.showToast("password cannot be empty", Toast.LENGTH_SHORT)
//            } else if (confirmPassword.isNullOrEmpty()) {
//                context.showToast("confirm password cannot be empty", Toast.LENGTH_SHORT)
//            } else if (password != confirmPassword) {
//                context.showToast("password doesn't matched", Toast.LENGTH_SHORT)
//            } else if (!context.hasInternetConnection()) {
//                context.showToast("No internet connection", Toast.LENGTH_SHORT)
//            } else {
//                registerProcessStart.value = true
//            }
//        }
        registerProcessStart.value = true
    }

    fun register(username: String, email: String, password: String, confirmPassword: String) {
        isLoading.value = true
        compositeDisposable.add(
            Observable.create(ObservableOnSubscribe<RegisterResponse> {
                onSubscribeRegister(it, RegisterRequest(username, email, password, confirmPassword))
            })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onNext, this::onError)
        )

        //send otp
    }


    private fun onSubscribeRegister(
        emitter: ObservableEmitter<RegisterResponse>,
        registerRequest: RegisterRequest
    ) {

        sendOtpByEmail("shrestharajan162@gmail.com",generateRandomDigits(6))

//        val retrofitService = ApiClient().getRetrofit(ApiConstants.BASE_URL)
//            .create(ApiInterface::class.java)
//        retrofitService.register(registerRequest).enqueue(object :
//            Callback<Any> {
//            override fun onResponse(
//                call: Call<Any>,
//                response: Response<Any>
//            ) {
//                if (response.isSuccessful) {
//                    val registerResponse: com.nchl.moneytracker.data.model.Response =
//                        Jsons.fromJsonToObj(
//                            response.body().toString(),
//                            com.nchl.moneytracker.data.model.Response::class.java
//                        )
//                    if (registerResponse.data != null) {
//                        prepareRegisterResponse(emitter, registerResponse.data as Any)
//                    } else {
//                        if (registerResponse.code == "401")
//                            emitter.onError(TrackerException(TrackerError.USER_REGSITER_FAILED))
//                        else
//                            emitter.onError(Exception("Error Code :: ${registerResponse.code}. ${registerResponse.message}"))
//                    }
//                } else {
//                    //emitter.onError(TrackerException(TrackerError.USER_REGSITER_FAILED))
//                }
//            }
//
//            override fun onFailure(call: Call<Any>, t: Throwable) {
//                emitter.onError(TrackerException(TrackerError.USER_REGSITER_FAILED))
//            }
//        })

        emitter.onComplete()
    }

    private fun prepareRegisterResponse(emitter: ObservableEmitter<RegisterResponse>, data: Any) {
        val responseJson = Jsons.toJsonObj(data)
        val response = Jsons.fromJsonToObj(responseJson, RegisterResponse::class.java)
        emitter.onNext(response)
    }

    private fun onNext(registerResponse: RegisterResponse) {
        isLoading.value = false
    }


    private fun onError(throwable: Throwable) {
        throwable.printStackTrace()
        isLoading.value = false
        if (throwable is TrackerException) {
            Logger.getLogger(TAG).debug(throwable.trackerError.errorMessage)
            val tackerException = throwable.trackerError
            if (tackerException == TrackerError.USER_REGSITER_FAILED)
                message.value = tackerException.errorMessage
            else
                message.value = tackerException.errorMessage
        } else {
            throwable.message?.let { Logger.getLogger(TAG).debug(it) }
            message.value = throwable.message
        }
    }
}