package com.example.pliin.apppliin.data.database.dataconverters

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
    @TypeConverter
    fun toDate(date: Long): Date {
        return Date(date)
    }
}