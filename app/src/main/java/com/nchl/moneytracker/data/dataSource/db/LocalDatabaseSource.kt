package com.nchl.moneytracker.data.dataSource.db

import com.nchl.moneytracker.domain.model.Category
import com.nchl.moneytracker.domain.model.DatabaseReponse
import com.nchl.moneytracker.domain.model.User
import com.nchl.moneytracker.presentation.model.ExpenseCategory
import io.reactivex.Observable

interface LocalDatabaseSource {
    fun getUsers(): Observable<List<User>>
    fun getCategories(): Observable<List<Category>>
    fun saveCategories(categories:List<ExpenseCategory>): Observable<DatabaseReponse>
}