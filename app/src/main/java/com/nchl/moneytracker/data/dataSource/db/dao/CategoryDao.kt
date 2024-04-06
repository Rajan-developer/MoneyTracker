package com.nchl.moneytracker.data.dataSource.db.dao

import androidx.room.*
import com.nchl.moneytracker.domain.model.Category
import com.nchl.moneytracker.domain.model.User
import com.nchl.moneytracker.presentation.model.ExpenseCategory


@Dao
interface CategoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(categories: List<Category>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category: Category): Long

    @Query("SELECT id, category_name,category_type,category_type  FROM categories")
    fun getCategoryList(): List<Category>

    @Query("SELECT * FROM categories WHERE category_type = :categoryType")
    fun getCategoriesByType(categoryType: String): List<Category>

    @Query("DELETE FROM categories WHERE id = :categoryId")
    fun deleteCategoryById(categoryId: String)

    @Update
    fun update(category: Category): Int

    @Query("DELETE FROM categories WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("DELETE FROM categories")
    fun nukeTableData()


    @Query("UPDATE categories SET category_name =:name WHERE  category_type =:type AND id =:id AND category_icon =:icon")
    fun updateCategory(name: String, type: String, icon: ByteArray, id: String)

}