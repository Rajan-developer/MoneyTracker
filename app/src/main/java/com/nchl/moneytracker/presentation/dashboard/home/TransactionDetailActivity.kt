package com.nchl.moneytracker.presentation.dashboard.home

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.nchl.moneytracker.BR
import com.nchl.moneytracker.R
import com.nchl.moneytracker.databinding.ActivtyTransactionDetailBinding
import com.nchl.moneytracker.presentation.base.AppBaseActivity
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import com.nchl.moneytracker.presentation.model.ExpenseTransaction
import java.util.*
import kotlin.collections.ArrayList


@RequiresApi(Build.VERSION_CODES.O)
class TransactionDetailActivity :
    AppBaseActivity<ActivtyTransactionDetailBinding, TransactionDetailViewModel>(),
    CategorySelectionDialog.CategorySelectionDialogListener {

    private lateinit var binding: ActivtyTransactionDetailBinding
    private var isEditing: Boolean = true
    private var title: String = ""
    var categoryList: ArrayList<ExpenseCategory> = arrayListOf()

    private var categorySelectionDialog: CategorySelectionDialog? = null

    companion object {
        const val TITLE = "title"
        const val TRANSACTION = "transaction"
        const val EDITING = "editing"

        fun getLaunchIntent(
            context: Context,
            title: String,
            transaction: ExpenseTransaction,
            editing: Boolean = true
        ): Intent {
            val intent = Intent(context, TransactionDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putString(TITLE, title)
            bundle.putParcelable(TRANSACTION, transaction)
            bundle.putBoolean(EDITING, editing)
            intent.putExtra("bundle", bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivtyTransactionDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataFromBundle()
        initViews()
        initObserver()
    }

    private fun getDataFromBundle() {
        val intent = intent
        val bundle = intent.getBundleExtra("bundle")

        if (bundle != null) {
            isEditing = bundle.getBoolean(EDITING)
            title = bundle.getString(TITLE).toString()
            getViewModel().expenseTransaction.value =
                bundle.getParcelable<ExpenseTransaction>(TRANSACTION)
        }
    }

    override fun onResume() {
        super.onResume()

        if (!isEditing) {
            binding.expenseRadioButton.isChecked = true
            getViewModel().setSelectedCategoryType("0")
            getViewModel().setSelectedDate()
            getViewModel().setSelectedTime()
        } else {
            setExpenseCategory()
            getViewModel().expenseTransaction.value?.date?.let { getViewModel().setSelectedDate(it) }
            getViewModel().expenseTransaction.value?.time?.let { getViewModel().setSelectedTime(it) }
        }

    }

    private fun initViews() {

        binding.tvTitle.text = title
        binding.rvCategoryOption.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.expenseRadioButton -> {
                    getViewModel().setSelectedCategoryType("0")
                    binding.textInputEtCategoryName.setText("")
                }
                R.id.incomeRadioButton -> {
                    getViewModel().setSelectedCategoryType("1")
                    binding.textInputEtCategoryName.setText("")
                }
            }
        }

        binding.layoutDate.setOnClickListener {
            showDatePickerDialog()
        }

        binding.layoutTime.setOnClickListener {
            showTimePickerDialog()
        }

        binding.textInputEtCategoryName.setOnClickListener {

            this.categorySelectionDialog = CategorySelectionDialog.newInstance(
                "Select Category",
                categoryList
            )
            this.categorySelectionDialog?.show(
                supportFragmentManager,
                CategorySelectionDialog.TAG
            )
        }


        binding.btnConfirm.setOnClickListener {
            getViewModel().validateALlTransactionField(
                binding.textInputEtCategoryName.text.toString(),
                binding.textInputEtTransactionAmount.text.toString(),
                binding.textInputEtTransactionRemark.text.toString()
            )
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }

        binding.ivBack.setOnClickListener {
            finish()
        }
    }

    private fun initObserver() {
        getViewModel().selectedDisplayDate.observe(this) {
            binding.selectedDate.text = it.toString()
        }

        getViewModel().selectedDisplayTime.observe(this) {
            binding.selectedTime.text = it.toString()
        }

        getViewModel().selectedExpenseCategoryType.observe(this) {
            if (it == "0") {
                getViewModel().getCategoriesByType("0")
            } else {
                getViewModel().getCategoriesByType("1")
            }
        }

        getViewModel().selectableCategory.observe(this) {
            categoryList = arrayListOf()
            categoryList = it.map { category ->
                ExpenseCategory(
                    category.name!!,
                    category.type!!,
                    category.icon!!,
                    category.id.toString(),
                )
            } as ArrayList<ExpenseCategory>
        }

        getViewModel().selectedCategoryName.observe(this) {
            binding.textInputEtCategoryName.setText(it)
        }

        getViewModel().isTransactionInserted.observe(this) {
            finish()
        }
    }

    private fun setExpenseCategory() {
        val categoryType = getViewModel().expenseTransaction.value?.categoryType
        if (categoryType == "0") {
            binding.expenseRadioButton.isChecked = true
            getViewModel().setSelectedCategoryType("0")
        } else {
            binding.incomeRadioButton.isChecked = true
            getViewModel().setSelectedCategoryType("1")
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog =
            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                println("Selected date " + selectedDate)
                getViewModel().setSelectedDate(selectedDate)

            }, year, month, day)

        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
            val selectedTime = "$selectedHour:$selectedMinute"
            println("Selected time " + selectedTime)
            getViewModel().setSelectedTime(selectedTime)

        }, hour, minute, false)
        // Set any additional properties of the time picker dialog
        // timePickerDialog.timePicker.is24HourView = true // Example: use 24-hour format
        timePickerDialog.show()
    }


    override fun getBindingVariable(): Int = BR.transacctionDetailViewModel


    override fun getLayout(): Int = R.layout.activty_transaction_detail

    override fun getViewModel(): TransactionDetailViewModel =
        ViewModelProviders.of(this)[TransactionDetailViewModel::class.java]

    override fun onCategorySelection(category: ExpenseCategory) {
        categorySelectionDialog?.dismiss()
        getViewModel().setSelectedCategory(category)
    }
}
