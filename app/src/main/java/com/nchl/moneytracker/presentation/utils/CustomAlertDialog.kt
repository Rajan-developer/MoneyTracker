package com.nchl.moneytracker.presentation.utils

import com.nchl.moneytracker.R

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nchl.moneytracker.presentation.utils.HelperUtils.isNullOrEmptyOrBlank
import de.hdodenhof.circleimageview.CircleImageView

class CustomAlertDialog(private val context: Context?) {

    private var alertDialog: Dialog? = null


    fun showConfirmationDialog(config: MessageConfig) {
        hide()
        val builder = MaterialAlertDialogBuilder(context as Activity)
        val view = LayoutInflater.from(context)
            .inflate(R.layout.custom_confirmation_dialog, null)
        val tvMessage = view.findViewById<AppCompatTextView>(R.id.tv_message)
        val tvTitle = view.findViewById<AppCompatTextView>(R.id.tv_title)
        val btnNegative = view.findViewById<MaterialButton>(R.id.btn_negative)
        val btnPositive = view.findViewById<MaterialButton>(R.id.btn_positive)
        tvTitle.text = config.title
        if (config.title.isNullOrEmptyOrBlank())
            tvTitle.visibility = View.GONE
        else
            tvTitle.visibility = View.VISIBLE
        btnNegative.text = config.negativeLabel
        tvMessage.text = config.message
        btnPositive.text = config.positiveLabel
        builder.setCancelable(false)
        btnPositive.setOnClickListener {
            config.onPositiveClick?.onClick(it)
            alertDialog?.dismiss()
        }
        btnNegative.setOnClickListener {
            config.onNegativeClick?.onClick(it)
            alertDialog?.dismiss()
        }
        builder.setView(view)
        alertDialog = builder.create()
        alertDialog?.show()
    }

   /* fun showAmountConfirmationDialog(config: MessageConfig) {
        hide()
        val builder = MaterialAlertDialogBuilder(context as Activity)
        val view = LayoutInflater.from(context)
            .inflate(R.layout.dialog_amount_confirmation, null)
        val tvMessage = view.findViewById<AppCompatTextView>(R.id.tv_message)
        val tvTitle = view.findViewById<AppCompatTextView>(R.id.tv_title)
        val tvCurrency = view.findViewById<AppCompatTextView>(R.id.tv_currency)
        val btnNegative = view.findViewById<MaterialButton>(R.id.btn_negative)
        val btnPositive = view.findViewById<MaterialButton>(R.id.btn_positive)
        tvCurrency.loadCurrency()
        tvTitle.text = config.title
        if (config.title.isNullOrEmptyOrBlank())
            tvTitle.visibility = View.GONE
        else
            tvTitle.visibility = View.VISIBLE
        btnNegative.text = config.negativeLabel
        tvMessage.text = config.message
        btnPositive.text = config.positiveLabel
        builder.setCancelable(false)
        btnPositive.setOnClickListener {
            config.onPositiveClick.onClick(it)
            alertDialog?.dismiss()
        }
        btnNegative.setOnClickListener {
            config.onNegativeClick.onClick(it)
            alertDialog?.dismiss()
        }
        builder.setView(view)
        alertDialog = builder.create()
        alertDialog?.show()
    }*/

    /*fun showNewSuccessMessage(config: MessageConfig){
        hide()
        val builder = AlertDialog.Builder(context as Activity)
        val view = LayoutInflater.from(context)
            .inflate(R.layout.new_custom_alert_dialog, null)
        val tvMessage = view.findViewById<AppCompatTextView>(R.id.tv_message)
        val btnPositive = view.findViewById<MaterialButton>(R.id.btn_positive)
        btnPositive.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#27AE60"))
        val ivIcon = view.findViewById<CircleImageView>(R.id.iv_icon)
        ivIcon.visibility = View.VISIBLE
        ivIcon.setImageResource(R.drawable.ic_check_circle_green_64dp)

        tvMessage.text = config.message
        if(config.positiveLabel.isNullOrEmptyOrBlank()){
            btnPositive.visibility = View.GONE
        } else {
            btnPositive.visibility = View.VISIBLE
            btnPositive.text = config.positiveLabel
        }
        builder.setCancelable(false)
        btnPositive.setOnClickListener {
            config.onPositiveClick.onClick(it)
            alertDialog?.dismiss()
        }
        builder.setView(view)
        alertDialog = builder.create()
        alertDialog?.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
    }*/

    fun showSuccessMessage(config: MessageConfig) {
        hide()
        val builder = AlertDialog.Builder(context as Activity)
        val view = LayoutInflater.from(context)
            .inflate(R.layout.custom_alert_dialog, null)
        val tvMessage = view.findViewById<AppCompatTextView>(R.id.tv_message)
        val btnPositive = view.findViewById<MaterialButton>(R.id.btn_positive)
        val ivIcon = view.findViewById<CircleImageView>(R.id.iv_icon)
        ivIcon.visibility = View.VISIBLE
        ivIcon.setImageResource(R.drawable.ic_green_circular_checked)

        tvMessage.text = config.message
        if(config.positiveLabel.isNullOrEmptyOrBlank()){
            btnPositive.visibility = View.GONE
        } else {
            btnPositive.visibility = View.VISIBLE
            btnPositive.text = config.positiveLabel
        }
        builder.setCancelable(false)
        btnPositive.setOnClickListener {
            config.onPositiveClick?.onClick(it)
            alertDialog?.dismiss()
        }
        builder.setView(view)
        alertDialog = builder.create()
        alertDialog?.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
    }

    fun updateButtonLabel(label: String){
        alertDialog?.let {
            val btnPositive =
                alertDialog?.findViewById<MaterialButton>(R.id.btn_positive)
            btnPositive?.let {
                if(label.isNullOrEmptyOrBlank()){
                    btnPositive.visibility = View.GONE
                } else {
                    btnPositive.visibility = View.VISIBLE
                    btnPositive.text = label
                }
            }
        }
        alertDialog?.show()
    }

    /*fun updateTimerMessage(message: String) {
        alertDialog?.let {
            val tvTimerMessage =
                alertDialog?.findViewById<AppCompatTextView>(R.id.tv_timer_message)
            tvTimerMessage?.let {
                if (message.isNotEmpty()) {
                    tvTimerMessage.text = message
                    tvTimerMessage.visibility = View.VISIBLE
                } else {
                    tvTimerMessage.text = ""
                    tvTimerMessage.visibility = View.GONE
                }
            }
        }
        alertDialog?.show()
    }*/

    /*fun showNormalMessage(config: MessageConfig) {
        hide()
        val builder = MaterialAlertDialogBuilder(context as Activity)
        val view = LayoutInflater.from(context)
            .inflate(R.layout.custom_alert_dialog, null)
        val tvMessage = view.findViewById<AppCompatTextView>(R.id.tv_message)
        val btnPositive = view.findViewById<MaterialButton>(R.id.btn_positive)

        tvMessage.text = config.message
        btnPositive.text = config.positiveLabel
        builder.setCancelable(false)
        btnPositive.setOnClickListener {
            alertDialog?.dismiss()
            config.onPositiveClick.onClick(it)
        }
        builder.setView(view)
        alertDialog = builder.create()
        alertDialog?.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
    }*/

    fun showFailureMessage(config: MessageConfig) {
        hide()
        val builder = MaterialAlertDialogBuilder(context as Activity)
        val view = LayoutInflater.from(context)
            .inflate(R.layout.custom_alert_dialog, null)
        val tvMessage = view.findViewById<AppCompatTextView>(R.id.tv_message)
        val btnPositive = view.findViewById<MaterialButton>(R.id.btn_positive)
        val ivIcon = view.findViewById<CircleImageView>(R.id.iv_icon)
        ivIcon.visibility = View.VISIBLE
        ivIcon.setImageResource(R.drawable.ic_red_circular_error)

        tvMessage.text = config.message
        btnPositive.text = config.positiveLabel
        builder.setCancelable(false)
        btnPositive.setOnClickListener {
            config.onPositiveClick?.onClick(it)
            alertDialog?.dismiss()
        }
        builder.setView(view)
        alertDialog = builder.create()
        alertDialog?.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
    }

   // @SuppressLint("MissingInflatedId")
    fun showNewFailureMessage(config: MessageConfig) {
        hide()
        val builder = MaterialAlertDialogBuilder(context as Activity)
        val view = LayoutInflater.from(context)
            .inflate(R.layout.new_custom_alert_dialog, null)
        val tvCondition= view.findViewById<AppCompatTextView>(R.id.tv_condition)
        tvCondition.text= "Failure !"
        tvCondition.setTextColor(Color.parseColor("#D50000"))
        val tvMessage = view.findViewById<AppCompatTextView>(R.id.tv_message)
        val btnPositive = view.findViewById<MaterialButton>(R.id.btn_positive)
        btnPositive.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#D50000"))
        val ivIcon = view.findViewById<CircleImageView>(R.id.iv_icon)
        ivIcon.visibility = View.VISIBLE
        ivIcon.setImageResource(R.drawable.ic_red_circular_error)

        tvMessage.text = config.message
        btnPositive.text = config.positiveLabel
        builder.setCancelable(false)
        btnPositive.setOnClickListener {
            config.onPositiveClick?.onClick(it)
            alertDialog?.dismiss()
        }
        builder.setView(view)
        alertDialog = builder.create()
        alertDialog?.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        alertDialog?.show()
    }

    fun hide() {
        if (alertDialog != null && alertDialog!!.isShowing)
            alertDialog!!.dismiss()
    }

    fun dismissDialog() {
        hide()
        alertDialog = null
    }
}