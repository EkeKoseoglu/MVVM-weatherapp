package tr.com.homesoft.wetherapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import tr.com.homesoft.wetherapp.data.local.entity.Location
import tr.com.homesoft.wetherapp.data.local.entity.Location.Companion.WEATHER_LOCATION_ID
import tr.com.homesoft.wetherapp.data.local.entity.Location.Companion.WEATHER_LOCATION_TABLE

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(location: Location)

    //Return value of the query can be accessed as a property
    @get:Query("SELECT * FROM $WEATHER_LOCATION_TABLE WHERE $COLUMN_ID = $WEATHER_LOCATION_ID")
    val weatherLocation: LiveData<Location>

    companion object {
        const val COLUMN_ID = "id"
    }


}