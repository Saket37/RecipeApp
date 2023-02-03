package com.example.recipeapp.utils

import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateHelper {
    fun Long.unixSecondsToText(format: String): String {
        Log.d("DATE_HELPER", this.toString())
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val date = LocalDateTime.ofInstant(
                Instant.ofEpochSecond(this),
                TimeZone.getTimeZone("Asia/Kolkata").toZoneId()
            )
            val formattedDate = date.format(DateTimeFormatter.ofPattern(format))
            Log.d("DATE_HELPER", formattedDate)
            formattedDate
        } else {
            val date = Date(this * 1000)
            val formattedDate = SimpleDateFormat(format, Locale.getDefault()).format(date)
            Log.d("DATE_HELPER", formattedDate)
            formattedDate
        }
    }
}

