package com.nchl.moneytracker.data.dataSource.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nchl.moneytracker.data.dataSource.db.dao.CategoryDao
import com.nchl.moneytracker.data.dataSource.db.dao.UsersDao
import com.nchl.moneytracker.domain.model.Category
import com.nchl.moneytracker.domain.model.User


@Database(
    entities = [User::class,Category::class],
    version = 13,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao(): UsersDao
    abstract fun getCategoryDao(): CategoryDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "moneytracker_db.db"
                )
                    .addMigrations(
                    )
                    .build()
            }
            if (!INSTANCE!!.isOpen) {
                INSTANCE!!.close()
                INSTANCE!!.openHelper.writableDatabase
            }
            return INSTANCE!!
        }
    }
}