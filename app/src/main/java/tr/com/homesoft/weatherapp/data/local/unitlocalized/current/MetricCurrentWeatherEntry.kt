package tr.com.homesoft.weatherapp.data.local.unitlocalized.current

import androidx.room.ColumnInfo
import tr.com.homesoft.weatherapp.data.local.entity.CurrentWeatherEntry.Companion.CONDITION_ICON
import tr.com.homesoft.weatherapp.data.local.entity.CurrentWeatherEntry.Companion.CONDITION_TEXT
import tr.com.homesoft.weatherapp.data.local.entity.CurrentWeatherEntry.Companion.HUMIDITY
import tr.com.homesoft.weatherapp.data.local.entity.CurrentWeatherEntry.Companion.WIND_DEGREE
import tr.com.homesoft.weatherapp.data.local.entity.CurrentWeatherEntry.Companion.WIND_DIRECTION


data class MetricCurrentWeatherEntry(

    @ColumnInfo(name = TEMP_C)
    override val temperature: Double,

    @ColumnInfo(name = CONDITION_TEXT)
    override val conditionText: String,

    @ColumnInfo(name = CONDITION_ICON)
    override val conditionIconUrl: String,

    @ColumnInfo(name = WIND_KpH)
    override val windSpeed: Double,

    @ColumnInfo(name = WIND_DIRECTION)
    override val windDirection: String,

    @ColumnInfo(name = PRECIP_Mm)
    override val precipitationVolume: Double,

    @ColumnInfo(name = FEELSLIKE_C)
    override val feelsLikeTemperature: Double,

    @ColumnInfo(name = VIS_KM)
    override val visibilityDistance: Double,

    @ColumnInfo(name = HUMIDITY)
    override val humidity: Int,

    @ColumnInfo(name = PRESSURE_Mb)
    override val pressure: Double,

    @ColumnInfo(name = WIND_DEGREE)
    override val windDegree: Int

) : UnitSpecificCurrentWeatherEntry {

    companion object {
        const val TEMP_C = "tempC"
        const val FEELSLIKE_C = "feelslikeC"
        const val VIS_KM = "visKm"
        const val PRESSURE_Mb = "pressureMb"
        const val WIND_KpH = "windKph"
        const val PRECIP_Mm = "precipMm"
    }
}


