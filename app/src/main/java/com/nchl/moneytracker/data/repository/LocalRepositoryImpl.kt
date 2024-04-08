package com.nchl.moneytracker.data.repository

import com.nchl.moneytracker.data.dataSource.db.LocalDatabaseSource
import com.nchl.moneytracker.data.dataSource.pref.SharedPreferenceSource
import com.nchl.moneytracker.domain.model.*
import com.nchl.moneytracker.domain.repository.LocalRepository
import com.nchl.moneytracker.presentation.model.CategorySum
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import com.nchl.moneytracker.presentation.model.ExpenseTransaction
import io.reactivex.Observable


class LocalRepositoryImpl(
    private val localDatabaseSource: LocalDatabaseSource,
    private val sharedPreferenceSource: SharedPreferenceSource
) : LocalRepository {
    override fun getUsers(): Observable<List<User>> = localDatabaseSource.getUsers()
    override fun getCategories(): Observable<List<Category>> = localDatabaseSource.getCategories()
    override fun getIncomeCategories(categoryType: String): Observable<List<Category>> = localDatabaseSource.getIncomeCategories(categoryType)
    override fun getExpenseCategories(categoryType: String): Observable<List<Category>> = localDatabaseSource.getExpenseCategories(categoryType)
    override fun getCategoriesByType(categoryType: String): Observable<List<Category>> = localDatabaseSource.getCategoriesByType(categoryType)
    override fun deleteCategoryById(categoryId: String): Observable<Boolean> = localDatabaseSource.deleteCategoryById(categoryId)
    override fun saveCategories(categories:List<ExpenseCategory>): Observable<DatabaseReponse>  = localDatabaseSource.saveCategories(categories)
    override fun updateCategory(category:ExpenseCategory): Observable<Boolean>  = localDatabaseSource.updateCategory(category)
    override fun addCategory(category:ExpenseCategory): Observable<Boolean>  = localDatabaseSource.addCategory(category)
    override fun addTransaction(transaction: ExpenseTransaction): Observable<Boolean>  = localDatabaseSource.addTransaction(transaction)
    override fun updateTransactionById(transaction: ExpenseTransaction): Observable<Boolean>  = localDatabaseSource.updateTransactionById(transaction)
    override fun deleteTransactionByID(transaction: ExpenseTransaction): Observable<Boolean>  = localDatabaseSource.deleteTransactionByID(transaction)
    override fun getTransactionByDate(transactionDate: String): Observable<List<Transaction>>  = localDatabaseSource.getTransactionByDate(transactionDate)
    override fun getTotalIncomeByDate(transactionDate: String,categoryType:String): Observable<Double>  = localDatabaseSource.getTotalIncomeByDate(transactionDate,categoryType)
    override fun getTransactionSumByCategory(transactionDate: String,categoryType:String): Observable<List<CategorySum>>  = localDatabaseSource.getTransactionSumByCategory(transactionDate,categoryType)
    override fun getTotalSumOfTransactionByCategory(categoryType:String): Observable<Double>  = localDatabaseSource.getTotalSumOfTransactionByCategory(categoryType)
}
