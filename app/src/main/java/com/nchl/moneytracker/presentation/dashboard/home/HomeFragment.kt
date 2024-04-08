package com.nchl.moneytracker.presentation.dashboard.home

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.FragmentHomeBinding
import com.nchl.moneytracker.presentation.base.AppBaseFragment
import com.nchl.moneytracker.presentation.dashboard.DashViewModel
import com.nchl.moneytracker.presentation.dashboard.transaction.TransactionDetailActivity
import com.nchl.moneytracker.presentation.model.ExpenseTransaction
import com.nchl.moneytracker.presentation.utils.AppUtility
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : AppBaseFragment(), SearchTransactionAdapter.Listener {

    private lateinit var binding: FragmentHomeBinding
    private val adapter = SearchTransactionAdapter(mutableListOf(), this)

    override fun getLayoutId(): Int = R.layout.fragment_home

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        hideSoftKeyboard(requireActivity(), requireView())

        getViewModel().getTransactionByDate(AppUtility.getCurrentDate())
        getViewModel().getTotalSumByDate(AppUtility.getCurrentDate(), "0")
        getViewModel().getTotalSumByDate(AppUtility.getCurrentDate(), "1")
        getViewModel().getTransactionSumByCategory(AppUtility.getCurrentDate(), "1")
    }

    override fun onResume() {
        super.onResume()
    }

    private fun initObservers() {

        getViewModel().selectedDisplayDate.observe(viewLifecycleOwner) {
            binding.selectedDate.text = it
            getViewModel().getTransactionByDate(getViewModel().selectedActualDate.value.toString())
            getViewModel().getTotalSumByDate(
                getViewModel().selectedActualDate.value.toString(),
                "0"
            )
            getViewModel().getTotalSumByDate(
                getViewModel().selectedActualDate.value.toString(),
                "1"
            )
        }

        getViewModel().transactionListByDate.observe(viewLifecycleOwner) {
            adapter.update(it)
            binding.tvTransactionCount.text = it.size.toString()
            if(it.size>0){
                binding.rvSearchTransaction.visibility = View.VISIBLE
                binding.noData.visibility = View.GONE
            }else{
                binding.rvSearchTransaction.visibility = View.GONE
                binding.noData.visibility = View.VISIBLE
            }
        }

        getViewModel().totalIncome.observe(viewLifecycleOwner) { income ->
            binding.debitTrCountTv.text = "Rs. $income"
            // Calculate total wallet balance
            val total = income - (getViewModel().totalExpense.value ?: 0.0)
            getViewModel().totalWalletBalance.value = total
        }

        getViewModel().totalExpense.observe(viewLifecycleOwner) { expense ->
            binding.credittTrCountTv.text = "Rs. $expense"
            // Calculate total wallet balance
            val total = (getViewModel().totalIncome.value ?: 0.0) - expense
            getViewModel().totalWalletBalance.value = total
        }
        getViewModel().totalWalletBalance.observe(viewLifecycleOwner) { totalBalance ->
            binding.tvWalletAmount.text = "Rs. $totalBalance"
        }
    }

    private fun initViews() {

        binding.layoutDatePicker.setOnClickListener {
            showDatePickerDialog()
        }

        val layoutManager = LinearLayoutManager(activity)
        binding.rvSearchTransaction.layoutManager = layoutManager
        binding.rvSearchTransaction.adapter = adapter

        binding.btnAddTransaction.setOnClickListener {
            navigateToTransactionDetail(ExpenseTransaction(), "Add Transaction", false)
        }
    }

    private fun navigateToTransactionDetail(
        transaction: ExpenseTransaction,
        title: String,
        editing: Boolean
    ) {
        val intent =
            TransactionDetailActivity.getLaunchIntent(
                requireContext(), title, transaction,
                editing
            )
        startActivityForResult(intent, TransactionDetailActivity.REQUEST_CODE)
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                getViewModel().setSelectedDate(selectedDate)

            }, year, month, day)

        datePickerDialog.show()
    }


    fun getViewModel() = ViewModelProviders.of(requireActivity())[DashViewModel::class.java]

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == TransactionDetailActivity.REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                if (getViewModel().selectedActualDate.value != null) {
                    getViewModel().getTransactionByDate(getViewModel().selectedActualDate.value.toString())
                    getViewModel().getTotalSumByDate(
                        getViewModel().selectedActualDate.value.toString(),
                        "0"
                    )
                    getViewModel().getTotalSumByDate(
                        getViewModel().selectedActualDate.value.toString(),
                        "1"
                    )
                } else {
                    getViewModel().getTransactionByDate(AppUtility.getCurrentDate())
                    getViewModel().getTotalSumByDate(AppUtility.getCurrentDate(), "0")
                    getViewModel().getTotalSumByDate(AppUtility.getCurrentDate(), "1")
                }
            }
        }
    }

    override fun onTransactionSelected(transaction: ExpenseTransaction) {
        navigateToTransactionDetail(transaction, "Edit Transaction", true)
    }
}