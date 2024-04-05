package com.nchl.moneytracker.data.dataSource.db.dao

import androidx.room.*
import com.nchl.moneytracker.domain.model.Category
import com.nchl.moneytracker.domain.model.User


@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categories: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category): Long

    @Query("SELECT id, category_name,category_type,category_type  FROM categories")
    fun getCategoryList(): List<Category>

    @Update
    fun update(category: Category): Int

    @Query("DELETE FROM categories WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("DELETE FROM categories")
    fun nukeTableData()

}