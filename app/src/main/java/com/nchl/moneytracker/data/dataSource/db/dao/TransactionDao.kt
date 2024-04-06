package com.nchl.moneytracker.data.dataSource.db.dao

import androidx.room.*
import com.nchl.moneytracker.domain.model.Transaction

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transactions: List<Transaction>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: Transaction): Long

    @Query("SELECT id, category_id,category_name,category_type,description,transaction_amount,date,time  FROM Transactions")
    fun getTransactionList(): List<Transaction>

    @Update
    fun update(transactions: Transaction): Int

    @Query("DELETE FROM transactions WHERE id = :id")
    fun deleteById(id: Long): Int

    @Query("DELETE FROM transactions")
    fun nukeTableData()
}