package com.nchl.moneytracker.domain.model

import android.content.ContentValues
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nchl.moneytracker.presentation.utils.AppUtility
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
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
    var amount: Double? = null,
    @ColumnInfo(name = DATE)
    var date: Date? = null,
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
                    transaction.amount = it.getAsString(AMOUNT).toDouble()
                if (it.containsKey(DATE))
                    transaction.date = stringToDate(it.getAsString(DATE), "d/M/yyyy")
                if (it.containsKey(TIME))
                    transaction.time = it.getAsString(TIME)

                return transaction
            }
        }


        fun stringToDate(dateString: String, format: String): Date {
            println("Original date : " + dateString)
            val formatter = SimpleDateFormat(format, Locale.getDefault())
            var date: Date
            try {
                date = formatter.parse(dateString)
            } catch (e: Exception) {
                e.printStackTrace()
                date = AppUtility.stringToDate(dateString, "EEE MMM dd HH:mm:ss zzz yyyy")
            }
            println("Converted date : " + date)
            return date
        }

    }
}