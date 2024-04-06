package global.citytech.finpos.merchant.extensions

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.button.MaterialButton
import global.citytech.easydroid.core.utils.Jsons


fun Application.hasInternetConnection(): Boolean {
    val connectivity = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val info = connectivity.allNetworkInfo
    for (i in info.indices)
        if (info[i].state == NetworkInfo.State.CONNECTED) {
            return true
        }

    return false
}

fun MaterialButton.handleDebounce() {
    this.isEnabled = false
    android.os.Handler().postDelayed({
        this.isEnabled = true
    }, 500)
}