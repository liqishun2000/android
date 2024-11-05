package com.example.core.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object TimeUtils{

    //统一字符串格式
    private val locale = Locale.US

    private const val YEAR = "yyyy"
    private const val MONTH_DAY = "MM-dd"
    private const val HOUR_MIN = "HH:mm"

    //example
    fun getYear() = formatMilliseconds(System.currentTimeMillis(), YEAR)

    private fun formatMilliseconds(
        timeMillis: Long,
        style: String,
        timeZone: String = TimeZone.getDefault().id //当前时区
    ): String {
        val date = Date(timeMillis)
        val sdf = SimpleDateFormat(style, locale)
        sdf.timeZone = TimeZone.getTimeZone(timeZone) // 设置时区

        return sdf.format(date)
    }

}