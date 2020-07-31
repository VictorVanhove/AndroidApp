package com.hogent.database

import androidx.room.TypeConverter
import java.util.*

class Converters {

    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long {
        return calendar.timeInMillis
    }

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar {
        return Calendar.getInstance().apply { timeInMillis = value }
    }
}