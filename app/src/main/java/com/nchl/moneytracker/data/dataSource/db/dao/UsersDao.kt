package com.nchl.moneytracker.data.dataSource.db.dao

import androidx.room.*
import com.nchl.moneytracker.domain.model.User


@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(user: User): Long

    @Query("SELECT id, name  FROM users")
    fun getUserList(): List<User>

    @Update
    fun update(user: User): Int

    @Query("DELETE FROM users WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("DELETE FROM users")
    fun nukeTableData()

}