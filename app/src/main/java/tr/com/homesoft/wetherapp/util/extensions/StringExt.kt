package tr.com.homesoft.wetherapp.util.extensions

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

fun String.formatDate(pattern: String): String {
    val local = LocalDate.parse(this)
    val formatter = DateTimeFormatter.ofPattern(pattern)
    return local.format(formatter)
}