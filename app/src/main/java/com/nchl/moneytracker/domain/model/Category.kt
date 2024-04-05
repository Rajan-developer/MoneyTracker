package com.nchl.moneytracker.domain.model

import android.content.ContentValues
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = Category.TABLE_NAME)
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Long = 0,
    @ColumnInfo(name = CATEGORY_NAME)
    var name: String? = null,
    @ColumnInfo(name = CATEGORY_TYPE)
    var type: String? = null,
    @ColumnInfo(name = CATEGORY_ICON)
    var icon: ByteArray? = null,

    ) {

    companion object {
        const val TABLE_NAME = "categories"
        const val COLUMN_ID = "id"
        const val CATEGORY_NAME = "category_name"
        const val CATEGORY_TYPE = "category_type"
        const val CATEGORY_ICON = "category_icon"

        fun fromContentValues(values: ContentValues): Category {
            values.let {
                val category = Category()
                if (it.containsKey(CATEGORY_NAME))
                    category.name = it.getAsString(CATEGORY_NAME)
                if (it.containsKey(CATEGORY_TYPE))
                    category.type = it.getAsString(CATEGORY_TYPE)
                if (it.containsKey(CATEGORY_TYPE))
                    category.icon = it.getAsByteArray(CATEGORY_ICON)

                return category
            }
        }

    }
}