package com.nchl.moneytracker.data.repository

import com.nchl.moneytracker.data.dataSource.db.LocalDatabaseSource
import com.nchl.moneytracker.data.dataSource.pref.SharedPreferenceSource
import com.nchl.moneytracker.domain.model.Category
import com.nchl.moneytracker.domain.model.DatabaseReponse
import com.nchl.moneytracker.domain.model.User
import com.nchl.moneytracker.domain.repository.LocalRepository
import com.nchl.moneytracker.presentation.model.ExpenseCategory
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
}
