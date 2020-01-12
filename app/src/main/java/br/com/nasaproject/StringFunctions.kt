package br.com.nasaproject

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.converteData(): String {

    val formater = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    formater.isLenient = false
    val date = formater.parse(this)

    val formatBR: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val dateFormated: String = formatBR.format(date)

    return dateFormated
}