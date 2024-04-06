package com.nchl.moneytracker.domain.repository

import com.nchl.moneytracker.domain.model.Category
import com.nchl.moneytracker.domain.model.DatabaseReponse
import com.nchl.moneytracker.domain.model.User
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
}