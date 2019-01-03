package tr.com.homesoft.weatherapp.data.local.entity

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
) {
    companion object {
        const val CONDITION_PREFIX = "condition_"
    }
}

