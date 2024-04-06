package com.nchl.moneytracker.extensions

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


fun <T> Activity.openActivity(className: Class<T>, options: ActivityOptions) {
    val intent = Intent(this, className)
    this.startActivity(intent, options.toBundle())
}

fun <T> Activity.openActivity(className: Class<T>) {
    this.startActivity(Intent(this, className))
}

fun <T> Activity.openActivity(className: Class<T>, bundle: Bundle) {
    val intent = Intent(this, className)
    intent.putExtra("BUNDLE", bundle)
    this.startActivity(intent)
}

fun Activity.openActivity(packageName: String) {
    try {
        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        startActivity(launchIntent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Activity.doesAppExist(packageName: String): Boolean {
    return try {
        packageManager.getPackageInfo(packageName, 0)
        true
    } catch (e: PackageManager.NameNotFoundException) {
        false
    }
}

fun Activity.getBundle() = intent.getBundleExtra("BUNDLE")

fun Activity.hideKeyboard(editText: EditText) {
    val imm: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(editText.getWindowToken(), 0)
}