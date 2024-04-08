package com.nchl.moneytracker.presentation.register

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nchl.moneytracker.BR
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.ActivityRegisterBinding
import com.nchl.moneytracker.presentation.base.AppBaseActivity
import com.nchl.moneytracker.presentation.login.LoginActivity
import com.nchl.moneytracker.presentation.utils.log.Logger

@RequiresApi(Build.VERSION_CODES.O)
class RegisterActivity : AppBaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    private val logger = Logger(RegisterActivity::class.java.name)
    private lateinit var binding: ActivityRegisterBinding

    companion object {
        fun getLaunchIntent(
            context: Context,
        ): Intent {
            val intent = Intent(context, RegisterActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initObservers()
    }

    private fun initViews() {
        // Now you can access views using binding
        binding.btnSignUp.setOnClickListener {
            getViewModel().validateRegisterCredential(
                binding.etUsername.text.toString(),
                binding.etEmail.text.toString(),
                binding.etPassword.text.toString(),
                binding.etConfirmPassword.text.toString()
            )
        }

        binding.tvSignIn.setOnClickListener {
            navigateToLogin()
        }

    }

    private fun initObservers() {
        getViewModel().registerProcessStart.observe(this) {
            if (it) {
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(binding.etPassword.windowToken, 0)
                getViewModel().register(
                    binding.etUsername.text.toString(),
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString(),
                    binding.etConfirmPassword.text.toString()
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

    private fun navigateToLogin() {
        val intent = LoginActivity.getLaunchIntent(this)
        startActivity(intent)
        finish()
    }

    override fun getBindingVariable(): Int = BR.registerViewModel

    override fun getLayout(): Int = R.layout.activity_register

    override fun getViewModel(): RegisterViewModel =
        ViewModelProviders.of(this)[RegisterViewModel::class.java]
}