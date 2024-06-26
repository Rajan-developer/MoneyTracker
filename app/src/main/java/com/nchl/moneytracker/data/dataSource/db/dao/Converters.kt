package com.nchl.moneytracker.data.dataSource.db.dao

import androidx.room.TypeConverter
import java.util.*


class Converters {
    @TypeConverter
    fun fromDate(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(millisSinceEpoch: Long?): Date? {
        return millisSinceEpoch?.let { Date(it) }
    }
}