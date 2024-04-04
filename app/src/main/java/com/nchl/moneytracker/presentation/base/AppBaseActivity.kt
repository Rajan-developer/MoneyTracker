package com.nchl.moneytracker.presentation.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.nchl.moneytracker.R
import com.nchl.moneytracker.presentation.utils.CustomAlertDialog
import com.nchl.moneytracker.presentation.utils.CustomProgressDialog
import com.nchl.moneytracker.presentation.utils.MessageConfig
import global.citytech.easydroid.core.mvvm.BaseActivity
import java.lang.ref.WeakReference


abstract class AppBaseActivity<T : androidx.databinding.ViewDataBinding, V : androidx.lifecycle.ViewModel> :
    BaseActivity<T, V>() {
    private var customAlertDialog : CustomAlertDialog? = null
    private var customProgressDialog : CustomProgressDialog? = null
    private var toast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customAlertDialog = CustomAlertDialog(WeakReference(this).get())
        customProgressDialog = CustomProgressDialog(WeakReference(this))
        toast = Toast.makeText(this,"", Toast.LENGTH_LONG)
    }


    fun showConfirmationMessage(messageBuilder: MessageConfig.Builder) {
        customAlertDialog?.showConfirmationDialog(messageBuilder.build())
    }
    fun hideConfirmationMessage() {
        customAlertDialog?.dismissDialog()
    }


    fun showFailureMessage(messageBuilder: MessageConfig.Builder) {
        customAlertDialog?.showFailureMessage(messageBuilder.build())
    }

    fun showGreenPinFailureMessage(messageBuilder: MessageConfig.Builder) {
        customAlertDialog?.showNewFailureMessage(messageBuilder.build())
    }

    fun showSuccessMessage(messageBuilder: MessageConfig.Builder) {
        customAlertDialog?.showSuccessMessage(messageBuilder.build())
    }

   /* fun updateTimerMessage(message: String) {
        customAlertDialog?.updateTimerMessage(message)
    }*/

    fun updateButtonLabel(label: String) {
        customAlertDialog?.updateButtonLabel(label)
    }

   /* fun showNormalMessage(messageBuilder: MessageConfig.Builder) {
        customAlertDialog?.showNormalMessage(messageBuilder.build())
    }*/

    fun dismissDialog() {
        customAlertDialog?.dismissDialog()
    }

    fun showProgress(message: String? = this.getString(R.string.title_please_wait)) {
        customProgressDialog?.show(message!!)
    }

    fun showProgressIcon() {
        customProgressDialog?.show()
    }

    fun hideProgress() {
        customProgressDialog?.hide()
    }

    fun clearProgress() {
        this.hideProgress()
    }

    fun showToast(message:String?) {
        if (message.isNullOrEmpty()) {
            return
        }
        if (toast?.view?.isShown == true) {
            toast?.cancel()
        }
        toast?.setText(message)
        toast?.show()
    }

    fun showToast(message:String?,length: Int) {
        if (message.isNullOrEmpty()) {
            return
        }
        toast?.duration  = length
        if (toast?.view?.isShown == true) {
            toast?.cancel()
        }
        toast?.setText(message)
        toast?.show()
    }

    override fun onDestroy() {
        customAlertDialog?.dismissDialog()
        customAlertDialog = null
        customProgressDialog?.dismissDialog()
        customProgressDialog = null
        toast?.cancel()
        toast=null
        super.onDestroy()
    }

    fun hideSoftKeyboard(activity: Activity, view: View){
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}