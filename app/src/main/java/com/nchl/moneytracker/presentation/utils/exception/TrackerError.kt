package com.nchl.moneytracker.presentation.utils.exception


enum class TrackerError(
    val errorCode: Int,
    val errorMessage: String
) {
    USER_LOGIN_FAILED(8000, "Login Failed"),
    USER_REGSITER_FAILED(8001, "Register Failed"),
    OTP_VERIFICATION_FAILED(8002, "Otp Verification Failed"),
    USER_BACK_PRESSED(8005, "Back Pressed"),
    DEVICE_ERROR_INVALID_DATA(8004, "Invalid Data"),

    UNKNOWN(9999, "");
}