package tr.com.homesoft.weatherapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import tr.com.homesoft.weatherapp.data.local.converter.DateConverter
import tr.com.homesoft.weatherapp.data.local.entity.DayForecast.Companion.DAYFORECAST
import tr.com.homesoft.weatherapp.data.local.entity.WeeklyForecastEntry.Companion.WEEKLY_FORECAST_TABLE_NAME

@Entity(tableName = WEEKLY_FORECAST_TABLE_NAME)
data class WeeklyForecastEntry(
    @TypeConverters(DateConverter::class)
    val date: String,
    @PrimaryKey
    @SerializedName("date_epoch")
    val dateEpoch: Long,
    @Embedded(prefix = DAYFORECAST)
    @SerializedName("day")
    val dayForecast: DayForecast
) {
    companion object {
        const val WEEKLY_FORECAST_TABLE_NAME = "weekly"
    }
}

