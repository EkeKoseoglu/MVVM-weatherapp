package tr.com.homesoft.wetherapp.data.local.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import tr.com.homesoft.wetherapp.data.local.entity.DayForecast.Companion.DAYFORECAST
import tr.com.homesoft.wetherapp.data.local.entity.WeeklyForecastEntry.Companion.WEEKLY_FORECAST_TABLE_NAME

@Entity(tableName = WEEKLY_FORECAST_TABLE_NAME)
data class WeeklyForecastEntry(
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

