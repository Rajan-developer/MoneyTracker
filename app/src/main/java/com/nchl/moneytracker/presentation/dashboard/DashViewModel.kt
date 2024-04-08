package com.nchl.moneytracker.presentation.dashboard

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.nchl.moneytracker.R
import com.nchl.moneytracker.data.repository.LocalRepositoryImpl
import com.nchl.moneytracker.domain.usecase.LocalDataUseCase
import com.nchl.moneytracker.framework.dataSource.LocalDatabaseSourceImpl
import com.nchl.moneytracker.framework.dataSource.PreferenceManager
import com.nchl.moneytracker.presentation.login.LoginViewModel
import com.nchl.moneytracker.presentation.model.CategorySum
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import com.nchl.moneytracker.presentation.model.ExpenseTransaction
import com.nchl.moneytracker.presentation.utils.AppUtility
import com.nchl.moneytracker.presentation.utils.AppUtility.drawableToByteArray
import com.nchl.moneytracker.presentation.utils.log.Logger
import global.citytech.easydroid.core.mvvm.BaseAndroidViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


@RequiresApi(Build.VERSION_CODES.O)
class DashViewModel(private val context: Application) : BaseAndroidViewModel(context) {

    private val TAG = Logger(LoginViewModel::class.java.name).toString()
    val firstTimeAppOpen = MutableLiveData<Boolean>()
    val incomeCategoryList by lazy { MutableLiveData<MutableList<ExpenseCategory>>() }
    val expenseCategoryList by lazy { MutableLiveData<MutableList<ExpenseCategory>>() }
    val categoryListByType by lazy { MutableLiveData<MutableList<ExpenseCategory>>() }
    val transactionListByDate by lazy { MutableLiveData<MutableList<ExpenseTransaction>>() }
    val totalIncome by lazy { MutableLiveData<Double>() }
    val totalExpense by lazy { MutableLiveData<Double>() }
    val totalWalletBalance by lazy { MutableLiveData<Double>() }
    val selectedDisplayDate = MutableLiveData<String>()
    val selectedDisplayChartDate = MutableLiveData<String>()
    val selectedActualDate = MutableLiveData<String>()
    val selectedActualChartDate = MutableLiveData<String>()
    val incomeChartList by lazy { MutableLiveData<MutableList<CategorySum>>() }
    val expenseChartList by lazy { MutableLiveData<MutableList<CategorySum>>() }
    val totalIncomeOfTransaction by lazy { MutableLiveData<Double>() }
    val totalExpenseOfTransaction by lazy { MutableLiveData<Double>() }
    val totalSavingOfTransaction by lazy { MutableLiveData<Double>() }

    var localDataUseCase: LocalDataUseCase = LocalDataUseCase(
        LocalRepositoryImpl(
            LocalDatabaseSourceImpl(),
            PreferenceManager
        )
    )

    fun isApplicationOpenFirstTime() {
        if (PreferenceManager.isApplicationOpenFirstTime(true)) {
            firstTimeAppOpen.value = true
            PreferenceManager.saveApplicationOpenFirstTime(false)
        }
    }

    /* ================================== CATEGORY ===============================================================*/

    fun saveInitialCategoryList() {
        var categories: List<ExpenseCategory> = staticCategories()
        this.compositeDisposable.add(
            this.localDataUseCase.saveCategories(categories)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Logger.getLogger(TAG)
                            .debug("Category inserting ${it.message}................")
                    },
                    {
                        Logger.getLogger(TAG).debug("Category insert ${it.message}................")
                    }
                )
        )
    }

    fun getAllCategoryFromDbTable() {
        this.compositeDisposable.add(
            this.localDataUseCase.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Logger.getLogger(TAG)
                            .debug("Category inserting ${it.size}................")
                    },
                    {
                        Logger.getLogger(TAG)
                            .debug("Category insert ${it.message}................")
                    }
                )
        )
    }

    fun getAllIncomeCategory(categoryType: String) {
        this.compositeDisposable.add(
            this.localDataUseCase.getCategoriesByType(categoryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Logger.getLogger(TAG)
                            .debug("Categories as per CategoryType $categoryType ${it.size}................")
                        isLoading.value = false
                        incomeCategoryList.value = it.reversed().map { category ->
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

    fun getAllExpenseCategory(categoryType: String) {
        this.compositeDisposable.add(
            this.localDataUseCase.getCategoriesByType(categoryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        isLoading.value = false
                        Logger.getLogger(TAG)
                            .debug("Categories as per CategoryType $categoryType ${it.size}................")

                        expenseCategoryList.value = it.reversed().map { category ->
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

    fun getCategoryByType(categoryType: String) {
        this.compositeDisposable.add(
            this.localDataUseCase.getCategoriesByType(categoryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Logger.getLogger(TAG)
                            .debug("Categories as per CategoryType $categoryType ${it.size}................")

                        categoryListByType.value = it.map { category ->
                            ExpenseCategory(
                                category.name!!,
                                category.type!!,
                                category.icon!!,
                                category.id.toString(),
                            )
                        } as ArrayList<ExpenseCategory>
                    },
                    {
                        Logger.getLogger(TAG)
                            .debug("Category insert ${it.message}................")
                    }
                )
        )
    }


    fun getTransactionSumByCategory(transactionDate: String, categoryType: String) {
        this.compositeDisposable.add(
            this.localDataUseCase.getTransactionSumByCategory(transactionDate, categoryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Logger.getLogger(TAG)
                            .debug("Categories as per CategoryType $categoryType ${it.size}................")
                        if(categoryType == "0"){
                            expenseChartList.value = it.map { categorySum ->
                                CategorySum(
                                    categorySum.categoryName,
                                    categorySum.transactionTotal,
                                )
                            } as ArrayList<CategorySum>
                        }else{
                            incomeChartList.value = it.map { categorySum ->
                                CategorySum(
                                    categorySum.categoryName,
                                    categorySum.transactionTotal,
                                )
                            } as ArrayList<CategorySum>
                        }
                    },
                    {
                        Logger.getLogger(TAG)
                            .debug("Category insert ${it.message}................")
                    }
                )
        )
    }

    fun deleteCategoryById(categoryId: String) {
        isLoading.value = true
        this.compositeDisposable.add(
            this.localDataUseCase.deleteCategoryById(categoryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        getAllIncomeCategory("1")
                        getAllExpenseCategory("0")
                    },
                    {
                        Logger.getLogger(TAG)
                            .debug("Category insert ${it.message}................")
                    }
                )
        )
    }

    fun updateCategory(category: ExpenseCategory) {
        isLoading.value = true
        this.compositeDisposable.add(
            this.localDataUseCase.updateCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        getAllIncomeCategory("1")
                        getAllExpenseCategory("0")
                    },
                    {
                        Logger.getLogger(TAG)
                            .debug("Category insert ${it.message}................")
                    }
                )
        )
    }

    fun addCategory(category: ExpenseCategory) {
        isLoading.value = true
        this.compositeDisposable.add(
            this.localDataUseCase.addCategory(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        getAllIncomeCategory("1")
                        getAllExpenseCategory("0")
                    },
                    {
                        Logger.getLogger(TAG)
                            .debug("Category insert ${it.message}................")
                    }
                )
        )
    }


    /* ================================== TRANSACTION ===============================================================*/


    fun getTransactionByDate(transactionDate: String) {
        this.compositeDisposable.add(
            this.localDataUseCase.getTransactionByDate(transactionDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        transactionListByDate.value = it.reversed().map { transaction ->
                            ExpenseTransaction(
                                transaction.categoryId!!,
                                transaction.categoryName!!,
                                transaction.categoryType!!,
                                transaction.description!!,
                                transaction.amount.toString(),
                                transaction.date.toString()!!,
                                transaction.time!!,
                                transaction.id.toString()!!,
                            )
                        } as ArrayList<ExpenseTransaction>
                    },
                    {
                        Logger.getLogger(TAG)
                            .debug("Transaction fetch ${it.message}................")
                    }
                )
        )
    }

    fun getTotalSumByDate(transactionDate: String, categoryType: String) {
        this.compositeDisposable.add(
            this.localDataUseCase.getTotalIncomeByDate(transactionDate, categoryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (categoryType == "0") {
                            totalExpense.value = it
                        } else {
                            totalIncome.value = it
                        }
                    },
                    {
                        Logger.getLogger(TAG)
                            .debug("Transaction fetch ${it.message}................")
                    }
                )
        )
    }

    fun getTotalSumOfTransactionByCategory(categoryType: String) {
        this.compositeDisposable.add(
            this.localDataUseCase.getTotalSumOfTransactionByCategory(categoryType)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (categoryType == "0") {
                            totalExpenseOfTransaction.value = it
                        } else {
                            totalIncomeOfTransaction.value = it
                        }
                    },
                    {
                        Logger.getLogger(TAG)
                            .debug("Transaction fetch ${it.message}................")
                    }
                )
        )
    }


    fun setSelectedDate(date: String = "") {
        var dateValue = ""
        dateValue = if (date.isNullOrEmpty() || date.isBlank()) {
            AppUtility.getCurrentDate()
        } else {
            date
        }
        selectedActualDate.value = dateValue
        val displayDate = AppUtility.convertDateToDisplayDate(dateValue)
        selectedDisplayDate.value = displayDate
    }

    fun setSelectedChartDate(date: String = "") {
        var dateValue = ""
        dateValue = if (date.isNullOrEmpty() || date.isBlank()) {
            AppUtility.getCurrentDate()
        } else {
            date
        }
        selectedActualChartDate.value = dateValue
        val displayDate = AppUtility.convertDateToDisplayDate(dateValue)
        selectedDisplayChartDate.value = displayDate
    }

    fun staticCategories(): List<ExpenseCategory> {
        var categories: ArrayList<ExpenseCategory> = arrayListOf()

        categories.add(
            ExpenseCategory(
                "Education",
                "0",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Clothing",
                "0",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Vehicle",
                "0",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Health",
                "0",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Shopping",
                "0",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Salary",
                "1",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Lottery",
                "1",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Rental",
                "1",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Sale",
                "1",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )

        categories.add(
            ExpenseCategory(
                "Refund",
                "1",
                drawableToByteArray(context, context.getDrawable(R.drawable.ic_cat_food)!!)
            )
        )
        return categories
    }
}