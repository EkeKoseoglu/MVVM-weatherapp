package tr.com.homesoft.weatherapp.data.local.converter

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

object DateConverter {

    @TypeConverter
    @JvmStatic
    fun strToDate(str: String?) = str?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }

    @TypeConverter
    @JvmStatic
    fun dateToString(date: LocalDate?) = date?.format(DateTimeFormatter.ISO_LOCAL_DATE)

}