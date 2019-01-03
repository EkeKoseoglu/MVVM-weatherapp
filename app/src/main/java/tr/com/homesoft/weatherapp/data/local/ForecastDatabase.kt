package tr.com.homesoft.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import tr.com.homesoft.weatherapp.data.local.converter.DateConverter
import tr.com.homesoft.weatherapp.data.local.dao.CurrentWeatherDao
import tr.com.homesoft.weatherapp.data.local.dao.LocationDao
import tr.com.homesoft.weatherapp.data.local.dao.WeeklyWeatherDao
import tr.com.homesoft.weatherapp.data.local.entity.CurrentWeatherEntry
import tr.com.homesoft.weatherapp.data.local.entity.WeatherLocation
import tr.com.homesoft.weatherapp.data.local.entity.WeeklyForecastEntry

@Database(
    entities = [CurrentWeatherEntry::class, WeeklyForecastEntry::class, WeatherLocation::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun currentWeatherDao(): CurrentWeatherDao

    abstract fun weeklyWeatherDao(): WeeklyWeatherDao

    abstract fun locationDao(): LocationDao

    companion object {
        private const val DATABASE_NAME = "weather-db"
        @Volatile
        private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                ForecastDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
    }

}