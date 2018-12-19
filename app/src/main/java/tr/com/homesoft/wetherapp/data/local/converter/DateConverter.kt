package tr.com.homesoft.wetherapp.data.local.converter

import androidx.room.TypeConverter
import java.util.*

class DateConverter {

    @TypeConverter
    fun toDate(timestamp: Long?) = timestamp?.let { Date(it) }

    @TypeConverter
    fun toTimestamp(date: Date?) = date?.time

}