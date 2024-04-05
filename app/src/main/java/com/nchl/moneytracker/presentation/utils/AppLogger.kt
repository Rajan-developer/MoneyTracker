package com.nchl.moneytracker.presentation.utils

import android.util.Log
import com.nchl.moneytracker.presentation.utils.log.AppState

class AppLogger {
    companion object {
        @JvmStatic fun log(message: String) {
            if (!message.isBlank() && message.isNotEmpty()) {
                Log.i(AppConstant.LOGGER_TAG, message)
            }
        }

        @JvmStatic fun debug(message: String) {
            if (!message.isBlank() && message.isNotEmpty() && AppState.getInstance().isDebug) {
                Log.i(AppConstant.LOGGER_TAG, message)
            }
        }
    }
}