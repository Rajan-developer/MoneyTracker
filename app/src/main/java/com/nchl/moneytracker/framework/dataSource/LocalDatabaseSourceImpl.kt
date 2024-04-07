package com.nchl.moneytracker.framework.dataSource

import android.content.ContentValues
import android.os.Build
import androidx.annotation.RequiresApi
import com.nchl.moneytracker.MoneyTrackerApplication
import com.nchl.moneytracker.data.dataSource.db.AppDatabase
import com.nchl.moneytracker.data.dataSource.db.LocalDatabaseSource
import com.nchl.moneytracker.domain.model.*
import com.nchl.moneytracker.presentation.model.CategorySum
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import com.nchl.moneytracker.presentation.model.ExpenseTransaction
import com.nchl.moneytracker.presentation.utils.AppUtility
import com.nchl.moneytracker.presentation.utils.AppUtility.dateStringToLong
import com.nchl.moneytracker.presentation.utils.AppUtility.stringToDate
import com.nchl.moneytracker.presentation.utils.log.Logger
import io.reactivex.Observable
import java.text.SimpleDateFormat
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
class LocalDatabaseSourceImpl : LocalDatabaseSource {

    private val logger = Logger(LocalDatabaseSourceImpl::class.java.simpleName)

    override fun getUsers(): Observable<List<User>> {
        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getUserDao().getUserList()
        }
    }

    override fun getCategories(): Observable<List<Category>> {
        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getCategoryDao()
                .getCategoryList()
        }
    }

    override fun getIncomeCategories(categoryType: String): Observable<List<Category>> {
        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getCategoryDao()
                .getCategoriesByType(categoryType)
        }
    }

    override fun getExpenseCategories(categoryType: String): Observable<List<Category>> {
        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getCategoryDao()
                .getCategoriesByType(categoryType)
        }
    }

    override fun getCategoriesByType(categoryId: String): Observable<List<Category>> {
        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getCategoryDao()
                .getCategoriesByType(categoryId)
        }
    }

    override fun deleteCategoryById(categoryType: String): Observable<Boolean> {
        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getCategoryDao()
                .deleteCategoryById(categoryType)
            true
        }
    }

    override fun saveCategories(categories: List<ExpenseCategory>): Observable<DatabaseReponse> {
        return Observable.fromCallable {
            if (!categories.isNullOrEmpty()) {
                for ((index, category) in categories.withIndex()) {
                    val contentValues = ContentValues()
                    contentValues.put(
                        Category.COLUMN_ID,
                        index.toString()
                    )
                    contentValues.put(
                        Category.CATEGORY_NAME,
                        category.name
                    )
                    contentValues.put(
                        Category.CATEGORY_TYPE,
                        category.type
                    )
                    contentValues.put(
                        Category.CATEGORY_ICON,
                        category.icon
                    )

                    val categoryDao =
                        AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getCategoryDao()
                    categoryDao.insert(Category.fromContentValues(contentValues))
                }
            }
            DatabaseReponse(DataBaseResult.SUCCESS, "Success")
        }
    }


    override fun updateCategory(category: ExpenseCategory): Observable<Boolean> {
        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getCategoryDao()
                .updateCategory(
                    category.name!!,
                    category.type!!,
                    category.icon!!,
                    category.id
                )
            true
        }
    }

    override fun addCategory(category: ExpenseCategory): Observable<Boolean> {
        return Observable.fromCallable {
            val contentValues = ContentValues()
            contentValues.put(
                Category.COLUMN_ID,
                category.id
            )
            contentValues.put(
                Category.CATEGORY_NAME,
                category.name
            )
            contentValues.put(
                Category.CATEGORY_TYPE,
                category.type
            )
            contentValues.put(
                Category.CATEGORY_ICON,
                category.icon
            )

            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getCategoryDao()
                .insert(Category.fromContentValues(contentValues))
            true
        }
    }

    override fun addTransaction(transaction: ExpenseTransaction): Observable<Boolean> {
        return Observable.fromCallable {
            val contentValues = ContentValues()
            contentValues.put(
                Transaction.COLUMN_ID,
                transaction.id
            )
            contentValues.put(
                Transaction.CATEGORY_ID,
                transaction.categoryId
            )
            contentValues.put(
                Transaction.CATEGORY_NAME,
                transaction.categoryName
            )
            contentValues.put(
                Transaction.CATEGORY_TYPE,
                transaction.categoryType
            )
            contentValues.put(
                Transaction.DATE,
                transaction.date
            )
            contentValues.put(
                Transaction.TIME,
                transaction.time
            )
            contentValues.put(
                Transaction.DESCRIPTION,
                transaction.description
            )
            contentValues.put(
                Transaction.AMOUNT,
                transaction.transactionAmount
            )

            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getTransactionDao()
                .insert(Transaction.fromContentValues(contentValues))
            true
        }
    }

    override fun updateTransactionById(transaction: ExpenseTransaction): Observable<Boolean> {

        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getTransactionDao()
                .updateTransaction(
                    transaction.categoryId,
                    transaction.categoryName,
                    transaction.categoryType,
                    transaction.date?.let { stringToDate(it, "d/M/yyyy") },
                    transaction.time,
                    transaction.transactionAmount,
                    transaction.description,
                    transaction.id
                )
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stringToDate(dateString: String, format: String): Date {
        println("Original date : " + dateString)
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        var date: Date
        try {
            date = formatter.parse(dateString)
        } catch (e: Exception) {
            e.printStackTrace()
            date = AppUtility.stringToDate(dateString, "EEE MMM dd HH:mm:ss zzz yyyy")
        }
        println("Converted date : " + date)
        return date
    }

    override fun deleteTransactionByID(transaction: ExpenseTransaction): Observable<Boolean> {
        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getTransactionDao()
                .deleteById(transaction.id.toLong())
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTransactionByDate(transactionDate: String): Observable<List<Transaction>> {

        var date = stringToDate(transactionDate, "d/M/yyyy")
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar[Calendar.DAY_OF_MONTH] = 1
        val startDateOfMonth = calendar.time
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val lastDateOfMonth = calendar.time

        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getTransactionDao()
                .getTransactionByDate(
                    dateStringToLong(startDateOfMonth.toString()),
                    dateStringToLong(lastDateOfMonth.toString())
                )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTotalIncomeByDate(transactionDate: String, categoryType:String): Observable<Double> {
        var date = stringToDate(transactionDate, "d/M/yyyy")
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar[Calendar.DAY_OF_MONTH] = 1
        val startDateOfMonth = calendar.time
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val lastDateOfMonth = calendar.time
        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getTransactionDao()
                .getTotalSumTransactionByDate(
                    categoryType,
                    dateStringToLong(startDateOfMonth.toString()),
                    dateStringToLong(lastDateOfMonth.toString())
                )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getTransactionSumByCategory(
        transactionDate: String,
        categoryType: String
    ): Observable<List<CategorySum>> {
        var date = stringToDate(transactionDate, "d/M/yyyy")
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar[Calendar.DAY_OF_MONTH] = 1
        val startDateOfMonth = calendar.time
        calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val lastDateOfMonth = calendar.time
        return Observable.fromCallable {
            AppDatabase.getInstance(MoneyTrackerApplication.INSTANCE).getTransactionDao()
                .getTransactionSumByCategory(
                    categoryType,
                    dateStringToLong(startDateOfMonth.toString()),
                    dateStringToLong(lastDateOfMonth.toString())
                )
        }
    }


}