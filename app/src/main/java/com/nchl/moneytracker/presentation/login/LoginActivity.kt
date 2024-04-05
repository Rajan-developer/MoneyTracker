package com.nchl.moneytracker.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nchl.moneytracker.BR
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.ActivityLoginBinding
import com.nchl.moneytracker.presentation.base.AppBaseActivity
import com.nchl.moneytracker.presentation.register.RegisterActivity
import com.nchl.moneytracker.presentation.utils.log.Logger

class LoginActivity : AppBaseActivity<ActivityLoginBinding, LoginViewModel>() {

    private val logger = Logger(LoginActivity::class.java.name)
    private lateinit var binding: ActivityLoginBinding


    companion object {
        fun getLaunchIntent(
            context: Context,
        ): Intent {
            val intent = Intent(context, LoginActivity::class.java)
            return intent
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initObservers()
    }

    private fun initViews() {
        binding.btnLogin.setOnClickListener {
//            getViewModel().validateLoginCredential(
//                binding.etEmail.text.toString(),
//                binding.etPassword.text.toString()
//            )
//            val intent = OtpActivity.getLaunchIntent(this)
//            startActivity(intent)
        }

        binding.tvSignUp.setOnClickListener{
            navigateToRegister()
        }
    }

    private fun initObservers() {
        getViewModel().loginProcessStart.observe(this) {
            if (it) {
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etPassword.windowToken, 0)
                getViewModel().login(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }


        getViewModel().isLoading.observe(this, Observer { isLoading ->
            if (isLoading) {
                showProgress()
            } else {
                hideProgress()
            }
        })
    }

    private fun navigateToRegister(){
        val intent = RegisterActivity.getLaunchIntent(this)
        startActivity(intent)
        finish()
    }

    override fun getBindingVariable(): Int = BR.loginViewModel

    override fun getLayout(): Int = R.layout.activity_login

    override fun getViewModel(): LoginViewModel =
        ViewModelProviders.of(this)[LoginViewModel::class.java]
}