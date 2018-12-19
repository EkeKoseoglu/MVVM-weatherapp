package tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly

import androidx.room.ColumnInfo
import tr.com.homesoft.wetherapp.data.local.entity.DayForecast.Companion.DAYFORECAST
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry.Companion.CONDITION_ICON
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry.Companion.CONDITION_TEXT
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry.Companion.DATE_TIME
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.UnitSpecificWeeklyForecastEntry.Companion.HUMIDITY

data class ImperialWeeklyForecastEntry(
    @ColumnInfo(name = DATE_TIME)
    override val date: String,
    @ColumnInfo(name = CONDITION_TEXT)
    override val conditionText: String,
    @ColumnInfo(name = CONDITION_ICON)
    override val conditionIconUrl: String,
    @ColumnInfo(name = MAX_TEMP_F)
    override val maxTemperature: Double,
    @ColumnInfo(name = MIN_TEMP_F)
    override val minTemperature: Double,
    @ColumnInfo(name = PRECIP_In)
    override val totalPrecip: Double,
    @ColumnInfo(name = HUMIDITY)
    override val humidity: Int,
    @ColumnInfo(name = VIS_MILES)
    override val visibility: Double,
    @ColumnInfo(name = TEMP_F)
    override val temperature: Double,
    @ColumnInfo(name = WIND_MpH)
    override val windSpeed: Double

) : UnitSpecificWeeklyForecastEntry {

    companion object {
        const val MAX_TEMP_F = DAYFORECAST + "maxtempF"
        const val MIN_TEMP_F = DAYFORECAST + "mintempF"
        const val TEMP_F = DAYFORECAST + "avgtempF"
        const val WIND_MpH = DAYFORECAST + "maxwindMph"
        const val VIS_MILES = DAYFORECAST + "avgvisMiles"
        const val PRECIP_In = DAYFORECAST + "totalprecipIn"
    }
}
