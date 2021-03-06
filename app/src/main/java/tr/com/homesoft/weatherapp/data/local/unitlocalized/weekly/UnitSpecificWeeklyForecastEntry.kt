package tr.com.homesoft.weatherapp.data.local.unitlocalized.weekly

import org.threeten.bp.LocalDate
import tr.com.homesoft.weatherapp.data.local.entity.Condition.Companion.CONDITION_PREFIX
import tr.com.homesoft.weatherapp.data.local.entity.DayForecast.Companion.DAYFORECAST

interface UnitSpecificWeeklyForecastEntry {

    val date: LocalDate
    val dateEpoch: Long
    val conditionText: String
    val conditionIconUrl: String
    val maxTemperature: Double
    val minTemperature: Double
    val totalPrecip: Double
    val humidity: Int
    val visibility: Double
    val temperature: Double
    val windSpeed: Double

    companion object {
        const val DATE_TIME = "date"
        const val DATE_EPOCH = "dateEpoch"
        const val HUMIDITY = DAYFORECAST + "avghumidity"
        const val CONDITION_TEXT = DAYFORECAST + CONDITION_PREFIX + "text"
        const val CONDITION_ICON = DAYFORECAST + CONDITION_PREFIX + "icon"
    }
}

