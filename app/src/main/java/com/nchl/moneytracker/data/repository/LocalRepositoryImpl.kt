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
    override fun saveCategories(categories:List<ExpenseCategory>): Observable<DatabaseReponse>  = localDatabaseSource.saveCategories(categories)
}
