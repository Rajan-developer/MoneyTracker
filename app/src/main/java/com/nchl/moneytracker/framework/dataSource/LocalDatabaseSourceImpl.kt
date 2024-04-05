package com.nchl.moneytracker.framework.dataSource

import com.nchl.moneytracker.MoneyTrackerApplication
import com.nchl.moneytracker.data.dataSource.db.AppDatabase
import com.nchl.moneytracker.data.dataSource.db.LocalDatabaseSource
import com.nchl.moneytracker.domain.model.Category
import com.nchl.moneytracker.domain.model.User
import com.nchl.moneytracker.presentation.utils.log.Logger
import io.reactivex.Observable


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
}