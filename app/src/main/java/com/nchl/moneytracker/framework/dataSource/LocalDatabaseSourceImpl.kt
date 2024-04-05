package com.nchl.moneytracker.framework.dataSource

import android.content.ContentValues
import com.nchl.moneytracker.MoneyTrackerApplication
import com.nchl.moneytracker.data.dataSource.db.AppDatabase
import com.nchl.moneytracker.data.dataSource.db.LocalDatabaseSource
import com.nchl.moneytracker.domain.model.Category
import com.nchl.moneytracker.domain.model.DataBaseResult
import com.nchl.moneytracker.domain.model.DatabaseReponse
import com.nchl.moneytracker.domain.model.User
import com.nchl.moneytracker.presentation.model.ExpenseCategory
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
}