package br.com.nasaproject

import java.text.SimpleDateFormat
import java.util.*

fun String.converteData(): Date {

    val formater = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
    formater.isLenient
    val date: Date = formater.parse(this)
    return date

}