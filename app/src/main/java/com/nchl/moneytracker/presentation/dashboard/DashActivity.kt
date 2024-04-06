package com.nchl.moneytracker.presentation.dashboard

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.nchl.moneytracker.BR
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.ActivityDashBinding
import com.nchl.moneytracker.presentation.base.AppBaseActivity
import com.nchl.moneytracker.presentation.dashboard.category.CategoryFragment
import com.nchl.moneytracker.presentation.utils.log.Logger


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class DashActivity : AppBaseActivity<ActivityDashBinding, DashViewModel>() {

    private val logger = Logger(DashActivity::class.java.name)
    private lateinit var binding: ActivityDashBinding
    private val categoryFragment: CategoryFragment  = CategoryFragment()


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

    override fun onResume() {
        super.onResume()
        getViewModel().isApplicationOpenFirstTime()
    }

    private fun initViews() {
        binding.bnvDashboard.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {

                }
                R.id.menu_chart -> {

                }
                R.id.menu_category -> {
                    openCategoryFragment()
                }
                R.id.menu_profile -> {

                }
                else -> {
                    return@setOnItemSelectedListener false
                }
            }
            true
        }
    }

    private fun initObservers() {

        getViewModel().firstTimeAppOpen.observe(this) {
            if (it) {
                getViewModel().saveInitialCategoryList()
            }
        }
    }


    private fun openCategoryFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_dashboard, categoryFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun getBindingVariable(): Int = BR.loginViewModel

    override fun getLayout(): Int = R.layout.activity_dash

    override fun getViewModel(): DashViewModel =
        ViewModelProviders.of(this)[DashViewModel::class.java]
}