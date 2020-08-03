package com.hogent.database.utilities

import androidx.room.TypeConverter
import java.util.*

internal class Converters {

    @TypeConverter
    fun calendarToDatestamp(calendar: Calendar): Long {
        return calendar.timeInMillis
    }

    @TypeConverter
    fun datestampToCalendar(value: Long): Calendar {
        return Calendar.getInstance().apply { timeInMillis = value }
    }
}