package com.nchl.moneytracker.presentation.dashboard.home

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.nchl.moneytracker.data.repository.LocalRepositoryImpl
import com.nchl.moneytracker.domain.usecase.LocalDataUseCase
import com.nchl.moneytracker.framework.dataSource.LocalDatabaseSourceImpl
import com.nchl.moneytracker.framework.dataSource.PreferenceManager
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import com.nchl.moneytracker.presentation.model.ExpenseTransaction
import com.nchl.moneytracker.presentation.utils.AppUtility
import com.nchl.moneytracker.presentation.utils.log.Logger
import global.citytech.easydroid.core.extension.showToast
import global.citytech.easydroid.core.mvvm.BaseAndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@RequiresApi(Build.VERSION_CODES.O)
class TransactionDetailViewModel(private val context: Application) : BaseAndroidViewModel(context) {

    private val TAG = Logger(TransactionDetailViewModel::class.java.name).toString()
    val expenseTransaction = MutableLiveData<ExpenseTransaction>()
    val selectedExpenseCategoryType = MutableLiveData<String>()
    val selectedDisplayDate = MutableLiveData<String>()
    val selectedDisplayTime = MutableLiveData<String>()
    val selectableCategory by lazy { MutableLiveData<MutableList<ExpenseCategory>>() }
    val selectedCategoryName = MutableLiveData<String>()
    val isTransactionInserted = MutableLiveData<Boolean>()
    var localDataUseCase: LocalDataUseCase = LocalDataUseCase(
        LocalRepositoryImpl(
            LocalDatabaseSourceImpl(),
            PreferenceManager
        )
    )

    fun setSelectedCategoryType(categoryType: String = "0") {
        selectedExpenseCategoryType.value = categoryType
        expenseTransaction.value?.categoryType = selectedExpenseCategoryType.value
        expenseTransaction.value?.categoryName = ""
    }

    fun setSelectedDate(date: String = "") {
        var dateValue = ""
        dateValue = if (date.isNullOrEmpty() || date.isBlank()) {
            AppUtility.getCurrentDate();
        } else {
            date
        }
        expenseTransaction.value?.date = dateValue
        val displayDate = AppUtility.convertDateToDisplayDate(dateValue)
        selectedDisplayDate.value = displayDate
    }

    fun setSelectedTime(time: String = "") {
        var timeValue = ""
        timeValue = if (time.isNullOrEmpty() || time.isBlank()) {
            AppUtility.getCurrentTime()
        } else {
            time
        }
        expenseTransaction.value?.time = timeValue
        val displayTime = AppUtility.convertTimeToDisplayTime(timeValue)
        selectedDisplayTime.value = displayTime
    }

    fun getCategoriesByType(categoryType: String) {
        this.compositeDisposable.add(
            this.localDataUseCase.getCategoriesByType(categoryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Logger.getLogger(TAG)
                            .debug("Categories as per CategoryType $categoryType ${it.size}................")
                        isLoading.value = false
                        selectableCategory.value = mutableListOf()
                        selectableCategory.value = it.reversed().map { category ->
                            ExpenseCategory(
                                category.name!!,
                                category.type!!,
                                category.icon!!,
                                category.id.toString(),
                            )
                        } as ArrayList<ExpenseCategory>
                    },
                    {
                        isLoading.value = false
                        Logger.getLogger(TAG)
                            .debug("Category insert ${it.message}................")
                    }
                )
        )
    }

    fun setSelectedCategory(category: ExpenseCategory) {
        expenseTransaction.value?.categoryId = category.id
        expenseTransaction.value?.categoryName = category.name
        selectedCategoryName.value = category.name
    }


    fun validateALlTransactionField(
        categoryName: String,
        transactionAmount: String,
        remark: String
    ) {
        if (categoryName.isNullOrEmpty()) {
            context.showToast("Select Category")
        } else if (transactionAmount.isNullOrEmpty()) {
            context.showToast("Enter transaction Amount")
        } else if (remark.isNullOrEmpty()) {
            context.showToast("Please enter remark")
        }else{
            expenseTransaction.value?.transactionAmount = transactionAmount
            expenseTransaction.value?.description = remark
            expenseTransaction.value?.let { addTransaction(it) }

        }
    }


    private fun addTransaction(transaction: ExpenseTransaction) {
        this.compositeDisposable.add(
            this.localDataUseCase.addTransaction(transaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        isLoading.value = false
                        isTransactionInserted.value = true
                    },
                    {
                        isLoading.value = false
                        Logger.getLogger(TAG)
                            .debug("Category insert ${it.message}................")
                    }
                )
        )
    }


}