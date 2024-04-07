package com.nchl.moneytracker.presentation.dashboard.chart

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.data.*
import com.google.android.material.tabs.TabLayout
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.FragmentChartBinding
import com.nchl.moneytracker.presentation.base.AppBaseFragment
import com.nchl.moneytracker.presentation.dashboard.DashViewModel
import com.nchl.moneytracker.presentation.utils.AppUtility
import java.util.*
import kotlin.collections.ArrayList


@RequiresApi(Build.VERSION_CODES.O)
class ChartFragment : AppBaseFragment() {


    private var tabArray: ArrayList<String> = arrayListOf()

    private lateinit var binding: FragmentChartBinding

    override fun getLayoutId(): Int = R.layout.fragment_chart

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        hideSoftKeyboard(requireActivity(), requireView())

        getViewModel().getTransactionSumByCategory(AppUtility.getCurrentDate(), "0")
        getViewModel().getTransactionSumByCategory(AppUtility.getCurrentDate(), "1")
    }

    private fun initObservers() {

    }

    private fun initViews() {

        initializeCategoryTabName()

        binding.viewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout))

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    println("TAB changed to " + it.position)
                    binding.viewPager.currentItem = it.position
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        setDynamicFragmentToTabLayout()

        binding.layoutDatePicker.setOnClickListener {
            showDatePickerDialog()
        }

        getViewModel().selectedDisplayChartDate.observe(viewLifecycleOwner){
            binding.selectedDate.text = it
            getViewModel().getTransactionSumByCategory(getViewModel().selectedActualChartDate.value.toString(), "0")
            getViewModel().getTransactionSumByCategory(getViewModel().selectedActualChartDate.value.toString(), "1")
        }

    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                getViewModel().setSelectedChartDate(selectedDate)

            }, year, month, day)

        datePickerDialog.show()
    }


    private fun initializeCategoryTabName() {
        tabArray = arrayListOf()
        tabArray.add("Expense")
        tabArray.add("Income")
    }

    private fun setDynamicFragmentToTabLayout() {
        for (i in 0 until tabArray.size) {
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText(tabArray[i]))
        }
        val mDynamicFragmentAdapter =
            ChartDynamicFragmentAdapter(childFragmentManager, binding.tabLayout.tabCount)

        binding.viewPager.adapter = mDynamicFragmentAdapter
        binding.viewPager.currentItem = 0
    }

    fun getViewModel() = ViewModelProviders.of(requireActivity())[DashViewModel::class.java]

    override fun onDestroyView() {
        super.onDestroyView()
    }

}



@RequiresApi(Build.VERSION_CODES.O)
class ChartDynamicFragmentAdapter internal constructor(
    fm: FragmentManager?,
    private val mNumOfTabs: Int
) :
    FragmentStatePagerAdapter(fm!!) {
    override fun getItem(position: Int): Fragment {

        return when (position) {
            0 -> {
                ExpenseChartFragment()
            }
            1 -> {
                IncomeChartFragment()
            }
            else -> throw IllegalArgumentException("Invalid tab position: $position")
        }
    }

    override fun getCount(): Int {
        return mNumOfTabs
    }
}