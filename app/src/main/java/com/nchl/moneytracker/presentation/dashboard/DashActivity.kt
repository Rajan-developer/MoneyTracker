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
import com.nchl.moneytracker.presentation.dashboard.chart.ChartFragment
import com.nchl.moneytracker.presentation.dashboard.home.HomeFragment
import com.nchl.moneytracker.presentation.dashboard.profile.ProfileFragment
import com.nchl.moneytracker.presentation.utils.log.Logger


@RequiresApi(Build.VERSION_CODES.O)
class DashActivity : AppBaseActivity<ActivityDashBinding, DashViewModel>() {

    private val logger = Logger(DashActivity::class.java.name)
    private lateinit var binding: ActivityDashBinding
    private val categoryFragment: CategoryFragment = CategoryFragment()

    private val homeFragment: HomeFragment = HomeFragment()
    private val chartFragment: ChartFragment = ChartFragment()
    private val profileFragment: ProfileFragment = ProfileFragment()
    private var backPressedTime: Long = 0


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
        openHomeFragment()
        binding.bnvDashboard.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> {
                    openHomeFragment()
                }
                R.id.menu_chart -> {
                    openChartFragment()
                }
                R.id.menu_category -> {
                    openCategoryFragment()
                }
                R.id.menu_profile -> {
                    openProfileFragment()
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

    private fun openHomeFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_dashboard, homeFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun openChartFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_dashboard, chartFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun openProfileFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fl_dashboard, profileFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    override fun getBindingVariable(): Int = BR.loginViewModel

    override fun getLayout(): Int = R.layout.activity_dash

    override fun getViewModel(): DashViewModel =
        ViewModelProviders.of(this)[DashViewModel::class.java]

    override fun onBackPressed() {
        val currentTime = System.currentTimeMillis()
        val timeout = 2000 // Define your timeout here (in milliseconds)

        if (backPressedTime + timeout > currentTime) {
            super.onBackPressed() // Call the default back button behavior (exit the app)
            finish()
        } else {
           showToast("Press back again to exit")
        }

        backPressedTime = currentTime
    }
}