package tr.com.homesoft.wetherapp.data.local.entity

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
) {
    companion object {
        const val CONDITION_PREFIX = "condition_"
    }
}

