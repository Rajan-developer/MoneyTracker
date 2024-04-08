package com.nchl.moneytracker.presentation.dashboard.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.FragmentProfileBinding
import com.nchl.moneytracker.presentation.base.AppBaseFragment
import com.nchl.moneytracker.presentation.dashboard.DashViewModel
import com.nchl.moneytracker.presentation.utils.AppUtility


@RequiresApi(Build.VERSION_CODES.O)
class ProfileFragment : AppBaseFragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()

        hideSoftKeyboard(requireActivity(), requireView())

        getViewModel().getTotalSumOfTransactionByCategory("0")
        getViewModel().getTotalSumOfTransactionByCategory("1")
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initObservers() {
        getViewModel().totalExpenseOfTransaction.observe(viewLifecycleOwner) { expense ->
            binding.tvTotalExpenseOfTransaction.text = "Rs. $expense"
            // Calculate total saving balance
            val total = (getViewModel().totalIncome.value ?: 0.0) - expense
            getViewModel().totalSavingOfTransaction.value = total


        }
        getViewModel().totalIncomeOfTransaction.observe(viewLifecycleOwner) { income ->
            binding.tvTotalIncomeOfTransaction.text = "Rs. $income"
            // Calculate total saving balance
            val total = income - (getViewModel().totalExpenseOfTransaction.value ?: 0.0)
            getViewModel().totalSavingOfTransaction.value = total
        }

        getViewModel().totalSavingOfTransaction.observe(viewLifecycleOwner) { totalBalance ->
            binding.tvTotalSavingOfTransaction.text = "Rs. $totalBalance"
        }
    }

    private fun initViews() {

    }

    fun getViewModel() = ViewModelProviders.of(requireActivity())[DashViewModel::class.java]
}