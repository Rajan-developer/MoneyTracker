package com.nchl.moneytracker.data.dataSource.db

import com.nchl.moneytracker.domain.model.Category
import com.nchl.moneytracker.domain.model.DatabaseReponse
import com.nchl.moneytracker.domain.model.User
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import io.reactivex.Observable

interface LocalDatabaseSource {
    fun getUsers(): Observable<List<User>>
    fun getCategories(): Observable<List<Category>>
    fun getIncomeCategories(categoryType: String): Observable<List<Category>>
    fun getExpenseCategories(categoryType: String): Observable<List<Category>>
    fun getCategoriesByType(categoryType: String): Observable<List<Category>>
    fun deleteCategoryById(categoryId: String): Observable<Boolean>
    fun saveCategories(categories:List<ExpenseCategory>): Observable<DatabaseReponse>
    fun updateCategory(categories:ExpenseCategory): Observable<Boolean>
    fun addCategory(categories:ExpenseCategory): Observable<Boolean>
}