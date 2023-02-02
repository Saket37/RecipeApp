package com.example.recipeapp.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object DateHelper {
    @RequiresApi(Build.VERSION_CODES.O)
    fun Long.unixSecondsToText(format: String): String {
        val date = LocalDateTime.ofInstant(
            Instant.ofEpochSecond(this),
            TimeZone.getTimeZone("Asia/Kolkata").toZoneId()
        )
        return date.format(DateTimeFormatter.ofPattern(format))
    }
}