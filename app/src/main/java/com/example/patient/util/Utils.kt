package com.example.patient.util

import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun convertTime(date: String): String{
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val hourFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        return hourFormat.format(
            parser.parse(date)
        )
    }
}