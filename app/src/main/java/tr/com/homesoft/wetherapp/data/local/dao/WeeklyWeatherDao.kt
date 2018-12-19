package tr.com.homesoft.wetherapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tr.com.homesoft.wetherapp.data.local.entity.WeeklyForecastEntry
import tr.com.homesoft.wetherapp.data.local.entity.WeeklyForecastEntry.Companion.WEEKLY_FORECAST_TABLE_NAME
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.ImperialWeeklyForecastEntry
import tr.com.homesoft.wetherapp.data.local.unitlocalized.weekly.MetricWeeklyForecastEntry

@Dao
interface WeeklyWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertAll(vararg data: WeeklyForecastEntry)

    @Query("DELETE FROM $WEEKLY_FORECAST_TABLE_NAME")
    fun deleteAll()

    @get:Query("SELECT * FROM $WEEKLY_FORECAST_TABLE_NAME")
    val metricWeeklyForecast: LiveData<List<MetricWeeklyForecastEntry>>

    @get:Query("SELECT * FROM $WEEKLY_FORECAST_TABLE_NAME")
    val imperialWeatherForecast: LiveData<List<ImperialWeeklyForecastEntry>>

    @Query("SELECT * FROM $WEEKLY_FORECAST_TABLE_NAME WHERE $COLUMN_DATE = :date")
    fun findMetricByDate(date: String): LiveData<MetricWeeklyForecastEntry>

    @Query("SELECT * FROM $WEEKLY_FORECAST_TABLE_NAME WHERE $COLUMN_DATE = :date")
    fun findImperialByDate(date: String): LiveData<ImperialWeeklyForecastEntry>


    companion object {
        const val COLUMN_DATE = "date"
    }
}