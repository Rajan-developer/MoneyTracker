package com.nchl.moneytracker.presentation.dashboard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.nchl.moneytracker.BR
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.ActivityDashBinding
import com.nchl.moneytracker.presentation.base.AppBaseActivity
import com.nchl.moneytracker.presentation.utils.log.Logger

class DashActivity : AppBaseActivity<ActivityDashBinding, DashViewModel>() {

    private val logger = Logger(DashActivity::class.java.name)
    private lateinit var binding: ActivityDashBinding


    companion object {
        fun getLaunchIntent(
            context: Context,
        ): Intent {
            val intent = Intent(context, DashActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initObservers()
    }

    private fun initViews() {

    }

    private fun initObservers() {

    }

    override fun getBindingVariable(): Int = BR.loginViewModel

    override fun getLayout(): Int = R.layout.activity_dash

    override fun getViewModel(): DashViewModel =
        ViewModelProviders.of(this)[DashViewModel::class.java]
}