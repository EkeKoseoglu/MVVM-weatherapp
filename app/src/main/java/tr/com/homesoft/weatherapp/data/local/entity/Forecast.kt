package tr.com.homesoft.weatherapp.data.local.entity

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val weeklyForecastList: List<WeeklyForecastEntry>
)
