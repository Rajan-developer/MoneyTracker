package com.nchl.moneytracker.presentation.utils

import android.content.Context
import android.widget.Toast
import global.citytech.easydroid.core.extension.showToast

object AppUtility {

     fun isValidEmail(context: Context, email: String): Boolean {
        var isValidEmail = false;
        return if (email.isNullOrEmpty()) {
            context.showToast("email cannot be empty", Toast.LENGTH_SHORT)
            isValidEmail
        } else {
            val regex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
            if (!regex.matches(email)) {
                isValidEmail = false
                context.showToast("invalid email", Toast.LENGTH_SHORT)
            } else {
                isValidEmail = true
            }
            return isValidEmail
        }

    }
}