package com.nchl.moneytracker.data.dataSource.db.dao

import androidx.room.*
import com.nchl.moneytracker.domain.model.Transaction
import com.nchl.moneytracker.presentation.model.CategorySum
import java.util.*

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transactions: List<Transaction>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(transaction: Transaction): Long

    @Query("UPDATE transactions SET category_id =:categoryId AND category_name=:categoryName AND category_type =:categoryType AND date =:date And time =:time AND transaction_amount=:transactionAmount AND description =:description WHERE  id =:id")
    fun updateTransaction(
        categoryId: String?,
        categoryName: String?,
        categoryType: String?,
        date: Date?,
        time: String?,
        transactionAmount: String?,
        description: String?,
        id: String
    )

    @Query("SELECT id, category_id,category_name,category_type,description,transaction_amount,date,time  FROM Transactions")
    fun getTransactionList(): List<Transaction>

    @Query("SELECT * FROM transactions WHERE date>= :fromDate AND date <= :toDate")
    fun getTransactionByDate(fromDate: Long, toDate: Long): List<Transaction>

    //gives transaction List as per category within date range
    @Query("SELECT category_name  AS categoryName,SUM(transaction_amount) as transactionTotal FROM transactions WHERE category_type = :categoryType AND date>= :fromDate AND date <= :toDate group by category_id")
    fun getTransactionSumByCategory(
        categoryType: String,
        fromDate: Long,
        toDate: Long
    ): List<CategorySum>

    @Query("SELECT SUM(transaction_amount) FROM transactions WHERE category_type = :categoryType AND date>= :fromDate AND date <= :toDate")
    fun getTotalSumTransactionByDate(categoryType: String, fromDate: Long, toDate: Long): Double

    @Update
    fun update(transactions: Transaction): Int

    @Query("DELETE FROM transactions WHERE id = :id")
    fun deleteById(id: Long)

    @Query("DELETE FROM transactions")
    fun nukeTableData()
}