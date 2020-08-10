package com.hogent.database.utilities

import org.junit.Assert
import org.junit.Test

import org.junit.Assert.*
import java.util.*

class ConvertersTest {

    @Test
    fun calendarToDatestamp() {

        val converters = Converters()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2020)
        calendar.set(Calendar.MONTH, 8)
        calendar.set(Calendar.DAY_OF_MONTH, 30)

        val expectedMillis = calendar.timeInMillis

        Assert.assertEquals(expectedMillis, converters.calendarToDatestamp(calendar))
    }

    @Test
    fun datestampToCalendar() {

        val converters = Converters()
        val millis = 1601480185938

        val calendarResult = converters.datestampToCalendar(millis)

        Assert.assertEquals(2020, calendarResult.get(Calendar.YEAR))
        Assert.assertEquals(8, calendarResult.get(Calendar.MONTH))
        Assert.assertEquals(30, calendarResult.get(Calendar.DAY_OF_MONTH))
    }
}