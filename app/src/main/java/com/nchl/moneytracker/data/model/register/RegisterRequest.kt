package com.nchl.moneytracker.data.model.register

class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
)