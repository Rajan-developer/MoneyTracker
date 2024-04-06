package com.nchl.moneytracker.domain.model

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Transaction.TABLE_NAME)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0,
    @ColumnInfo(name = CATEGORY_ID)
    var categoryId: String? = null,
    @ColumnInfo(name = CATEGORY_NAME)
    var categoryName: String? = null,
    @ColumnInfo(name = CATEGORY_TYPE)
    var categoryType: String? = null,
    @ColumnInfo(name = DESCRIPTION)
    var description: String? = null,
    @ColumnInfo(name = AMOUNT)
    var amount: String? = null,
    @ColumnInfo(name = DATE)
    var date: String? = null,
    @ColumnInfo(name = TIME)
    var time: String? = null,


    ) {

    companion object {
        const val TABLE_NAME = "transactions"
        const val COLUMN_ID = "id"
        const val CATEGORY_ID = "category_id"
        const val CATEGORY_NAME = "category_name"
        const val CATEGORY_TYPE = "category_type"
        const val DESCRIPTION = "description"
        const val AMOUNT = "transaction_amount"
        const val DATE = "date"
        const val TIME = "time"


        fun fromContentValues(values: ContentValues): Transaction {
            values.let {
                val transaction = Transaction()
                if (it.containsKey(CATEGORY_ID))
                    transaction.categoryId = it.getAsString(CATEGORY_ID)
                if (it.containsKey(CATEGORY_NAME))
                    transaction.categoryName = it.getAsString(CATEGORY_NAME)
                if (it.containsKey(CATEGORY_TYPE))
                    transaction.categoryType = it.getAsString(CATEGORY_TYPE)
                if (it.containsKey(DESCRIPTION))
                    transaction.description = it.getAsString(DESCRIPTION)
                if (it.containsKey(DESCRIPTION))
                    transaction.amount = it.getAsString(AMOUNT)
                if (it.containsKey(DATE))
                    transaction.date = it.getAsString(DATE)
                if (it.containsKey(TIME))
                    transaction.time = it.getAsString(TIME)

                return transaction
            }
        }

    }
}