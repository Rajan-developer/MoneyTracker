package com.nchl.moneytracker.domain.repository

import com.nchl.moneytracker.domain.model.Category
import com.nchl.moneytracker.domain.model.User
import io.reactivex.Observable

interface LocalRepository {
    fun getUsers(): Observable<List<User>>
    fun getCategories(): Observable<List<Category>>
}