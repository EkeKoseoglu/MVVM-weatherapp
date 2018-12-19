package tr.com.homesoft.wetherapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tr.com.homesoft.wetherapp.data.local.entity.CurrentWeatherEntry
import tr.com.homesoft.wetherapp.data.local.entity.CurrentWeatherEntry.Companion.CURRENT_WEATHER_ID
import tr.com.homesoft.wetherapp.data.local.entity.CurrentWeatherEntry.Companion.CURRENT_WEATHER_TABLE_NAME
import tr.com.homesoft.wetherapp.data.local.unitlocalized.current.ImperialCurrentWeatherEntry
import tr.com.homesoft.wetherapp.data.local.unitlocalized.current.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(currentWeatherEntry: CurrentWeatherEntry)

    //Return value of the query can be accessed as a property
    @get:Query("SELECT * FROM $CURRENT_WEATHER_TABLE_NAME WHERE $COLUMN_ID = $CURRENT_WEATHER_ID")
    val weatherMetric: LiveData<MetricCurrentWeatherEntry>

    @get:Query("SELECT * FROM $CURRENT_WEATHER_TABLE_NAME WHERE $COLUMN_ID = $CURRENT_WEATHER_ID")
    val weatherImperial: LiveData<ImperialCurrentWeatherEntry>

    companion object {
        const val COLUMN_ID = "id"
    }
}

