package com.nchl.moneytracker.domain.repository

import com.nchl.moneytracker.domain.model.*
import com.nchl.moneytracker.presentation.model.CategorySum
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import com.nchl.moneytracker.presentation.model.ExpenseTransaction
import io.reactivex.Observable

interface LocalRepository {
    fun getUsers(): Observable<List<User>>
    fun getCategories(): Observable<List<Category>>
    fun getIncomeCategories(categoryType: String): Observable<List<Category>>
    fun getExpenseCategories(categoryType: String): Observable<List<Category>>
    fun getCategoriesByType(categoryType: String): Observable<List<Category>>
    fun deleteCategoryById(categoryId: String): Observable<Boolean>
    fun saveCategories(categories:List<ExpenseCategory>):Observable<DatabaseReponse>
    fun updateCategory(category:ExpenseCategory):Observable<Boolean>
    fun addCategory(category:ExpenseCategory):Observable<Boolean>
    fun addTransaction(transaction: ExpenseTransaction):Observable<Boolean>
    fun updateTransactionById(transaction: ExpenseTransaction):Observable<Boolean>
    fun deleteTransactionByID(transaction: ExpenseTransaction):Observable<Boolean>
    fun getTransactionByDate(transactionDate: String):Observable<List<Transaction>>
    fun getTotalIncomeByDate(transactionDate: String,categoryType:String):Observable<Double>
    fun getTransactionSumByCategory(transactionDate: String,categoryType:String):Observable<List<CategorySum>>
    fun getTotalSumOfTransactionByCategory(categoryType:String):Observable<Double>
}