package com.nchl.moneytracker.presentation.utils

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.nchl.moneytracker.R
import java.lang.ref.WeakReference

class CustomProgressDialog(private val context: WeakReference<Context>, private val resTheme: Int? = null) {

    private var progressView: View? = null
    var progressDialog: AlertDialog? = null


    init {
        context.get().let {
            var dialogBuilder = AlertDialog.Builder(it!!)
            if(resTheme != null)
                dialogBuilder = AlertDialog.Builder(it, resTheme)
            progressView = LayoutInflater.from(it)
                .inflate(R.layout.custom_progress, null)
//            Glide.with(it).load("file:///android_asset/images/loading.gif")
//                .into(progressView!!.iv_progress)
            dialogBuilder.setView(progressView!!)
            dialogBuilder.setCancelable(false)
            progressDialog = dialogBuilder.create()
        }
    }

    fun show(message: String? = "") {
        if(progressDialog != null) {
//            progressView!!.tv_message.text = message
            progressDialog!!.show()
        }
    }

    fun hide(){
        if(progressDialog !=null && progressDialog!!.isShowing){
            progressDialog!!.dismiss()
        }
    }

    fun dismissDialog() {
        hide()
        progressDialog = null
    }

}
