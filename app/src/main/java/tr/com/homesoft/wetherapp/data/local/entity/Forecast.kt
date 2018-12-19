package tr.com.homesoft.wetherapp.data.local.entity

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val weeklyForcastList: List<WeeklyForecastEntry>
)
