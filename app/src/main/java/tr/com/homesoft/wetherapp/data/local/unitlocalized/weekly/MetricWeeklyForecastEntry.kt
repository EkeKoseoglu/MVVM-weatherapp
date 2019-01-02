package tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly

import androidx.room.ColumnInfo
import org.threeten.bp.LocalDate
import tr.com.homesoft.wetherapp.data.local.entity.DayForecast.Companion.DAYFORECAST
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry.Companion.CONDITION_ICON
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry.Companion.CONDITION_TEXT
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry.Companion.DATE_TIME
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry.Companion.HUMIDITY


data class MetricWeeklyForecastEntry(

    @ColumnInfo(name = DATE_TIME)
    override val date: LocalDate,

    @ColumnInfo(name = CONDITION_TEXT)
    override val conditionText: String,

    @ColumnInfo(name = CONDITION_ICON)
    override val conditionIconUrl: String,

    @ColumnInfo(name = MAX_TEMP_C)
    override val maxTemperature: Double,

    @ColumnInfo(name = MIN_TEMP_C)
    override val minTemperature: Double,

    @ColumnInfo(name = PRECIP_Mm)
    override val totalPrecip: Double,

    @ColumnInfo(name = HUMIDITY)
    override val humidity: Int,

    @ColumnInfo(name = VIS_KM)
    override val visibility: Double,

    @ColumnInfo(name = TEMP_C)
    override val temperature: Double,

    @ColumnInfo(name = WIND_KpH)
    override val windSpeed: Double
) : UnitSpecificWeeklyForecastEntry {


    companion object {
        const val MAX_TEMP_C = DAYFORECAST + "maxtempC"
        const val MIN_TEMP_C = DAYFORECAST + "mintempC"
        const val TEMP_C = DAYFORECAST + "avgtempC"
        const val WIND_KpH = DAYFORECAST + "maxwindKph"
        const val VIS_KM = DAYFORECAST + "avgvisKm"
        const val PRECIP_Mm = DAYFORECAST + "totalprecipMm"
    }
}



