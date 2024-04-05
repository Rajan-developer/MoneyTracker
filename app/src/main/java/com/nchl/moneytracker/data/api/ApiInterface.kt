package com.nchl.moneytracker.data.api

import com.nchl.moneytracker.data.model.login.LoginRequest
import com.nchl.moneytracker.data.model.register.RegisterRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("/authentication/login")
    fun login(
        @Body loginRequest: LoginRequest
    ): Call<Any>

    @POST("/authentication/register")
    fun register(
        @Body registerRequest: RegisterRequest
    ): Call<Any>

//    @POST("/authentication/verifyOtp")
//    fun verifyOtp(
//        @Body otpRequest: OtpRequest
//    ): Call<Any>

//    @POST("/authentication/resendOtp")
//    fun resendOtp(
//        @Body registerRequest: RegisterRequest
//    ): Call<Any>
}