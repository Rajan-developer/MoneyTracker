package com.nchl.moneytracker.presentation.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.nchl.moneytracker.R
import com.nchl.moneytracker.presentation.utils.CustomProgressDialog
import java.lang.ref.WeakReference

abstract class AppBaseFragment: Fragment() {

    private var customProgressDialog: CustomProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customProgressDialog = CustomProgressDialog(WeakReference(requireActivity()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    abstract fun getLayoutId(): Int

    fun showProgress(message: String? = this.getString(R.string.title_please_wait)) {
        customProgressDialog?.show(message!!)
    }

    fun hideProgress() {
        customProgressDialog?.hide()
    }

    fun hideSoftKeyboard(activity:Activity, view: View){
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

}