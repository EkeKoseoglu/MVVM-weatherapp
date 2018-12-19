package tr.com.homesoft.wetherapp.data.local.unitlocalized.current

import androidx.room.ColumnInfo
import tr.com.homesoft.wetherapp.data.local.entity.CurrentWeatherEntry.Companion.CONDITION_ICON
import tr.com.homesoft.wetherapp.data.local.entity.CurrentWeatherEntry.Companion.CONDITION_TEXT
import tr.com.homesoft.wetherapp.data.local.entity.CurrentWeatherEntry.Companion.HUMIDITY
import tr.com.homesoft.wetherapp.data.local.entity.CurrentWeatherEntry.Companion.WIND_DEGREE
import tr.com.homesoft.wetherapp.data.local.entity.CurrentWeatherEntry.Companion.WIND_DIRECTION

data class ImperialCurrentWeatherEntry(

    @ColumnInfo(name = TEMP_F)
    override val temperature: Double,

    @ColumnInfo(name = CONDITION_TEXT)
    override val conditionText: String,

    @ColumnInfo(name = CONDITION_ICON)
    override val conditionIconUrl: String,

    @ColumnInfo(name = WIND_MpH)
    override val windSpeed: Double,

    @ColumnInfo(name = WIND_DIRECTION)
    override val windDirection: String,

    @ColumnInfo(name = PRECIP_IN)
    override val precipitationVolume: Double,

    @ColumnInfo(name = FEELSLIKE_F)
    override val feelsLikeTemperature: Double,

    @ColumnInfo(name = VIS_MILES)
    override val visibilityDistance: Double,

    @ColumnInfo(name = HUMIDITY)
    override val humidity: Int,

    @ColumnInfo(name = PRESSURE_IN)
    override val pressure: Double,

    @ColumnInfo(name = WIND_DEGREE)
    override val windDegree: Int

) : UnitSpecificCurrentWeatherEntry {

    companion object {
        const val FEELSLIKE_F = "feelslikeF"
        const val TEMP_F = "tempF"
        const val VIS_MILES = "visMiles"
        const val PRECIP_IN = "precipIn"
        const val PRESSURE_IN = "pressureIn"
        const val WIND_MpH = "windMph"
    }
}

