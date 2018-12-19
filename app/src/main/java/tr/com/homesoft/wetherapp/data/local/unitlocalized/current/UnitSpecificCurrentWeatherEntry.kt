package tr.com.homesoft.wetherapp.data.local.unitlocalized.current

interface UnitSpecificCurrentWeatherEntry {
    val temperature: Double
    val conditionText: String
    val conditionIconUrl: String
    val windSpeed: Double
    val windDirection: String
    val windDegree: Int
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
    val humidity: Int
    val pressure: Double
}