package tr.com.homesoft.wetherapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import tr.com.homesoft.wetherapp.data.local.entity.Condition.Companion.CONDITION_PREFIX

@Entity(tableName = CurrentWeatherEntry.CURRENT_WEATHER_TABLE_NAME)
data class CurrentWeatherEntry(
    val cloud: Int,

    @Embedded(prefix = CONDITION_PREFIX)
    val condition: Condition,

    @SerializedName("feelslike_c")
    val feelslikeC: Double,

    @SerializedName("feelslike_f")
    val feelslikeF: Double,

    val humidity: Int,

    @SerializedName("is_day")
    val isDay: Int,

    @SerializedName("precip_in")
    val precipIn: Double,

    @SerializedName("precip_mm")
    val precipMm: Double,

    @SerializedName("pressure_in")
    val pressureIn: Double,

    @SerializedName("pressure_mb")
    val pressureMb: Double,

    @SerializedName("temp_c")
    val tempC: Double,

    @SerializedName("temp_f")
    val tempF: Double,

    val uv: Double,

    @SerializedName("vis_km")
    val visKm: Double,

    @SerializedName("vis_miles")
    val visMiles: Double,

    @SerializedName("wind_dir")
    val windDir: String,

    @SerializedName("wind_degree")
    val windDegree: Int,

    @SerializedName("wind_kph")
    val windKph: Double,

    @SerializedName("wind_mph")
    val windMph: Double
) {
    @PrimaryKey(autoGenerate = false)
    var id = CURRENT_WEATHER_ID

    companion object {
        const val CURRENT_WEATHER_TABLE_NAME = "current_weather"
        const val CURRENT_WEATHER_ID = 0
        const val CONDITION_TEXT = "${CONDITION_PREFIX}text"
        const val CONDITION_ICON = "${CONDITION_PREFIX}icon"
        const val HUMIDITY = "humidity"
        const val WIND_DIRECTION = "windDir"
        const val WIND_DEGREE = "windDegree"
    }
}


