package com.scandrug.scandrug.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object TimeUtil {
    private const val OPEN_TIME = "open_time"
    const val notificationDatePattern = "dd//MM/yy"
    const val notificationTimePattern = "hh:mm a"

    val todayDate: String
        get() {
            val c = Calendar.getInstance()
            @SuppressLint("SimpleDateFormat") val format = SimpleDateFormat("MMM,dd")
            return format.format(c.time)
        }

    private fun getFormattedDate(milliSec: Long): Date {
        val cal = Calendar.getInstance()
        cal.timeInMillis = milliSec
        /* int hours=getHours(date);
        cal.set(Calendar.HOUR_OF_DAY,hours);
        int min=getMins(date);
        cal.set(Calendar.MINUTE,min);
        int sec=getSeconds(date);
        cal.set(Calendar.SECOND,sec);*/return cal.time
    }

    private fun getSeconds(date: String): Int {
        val sec = date.substring(16, 18)
        return sec.toInt()
    }

    private fun getMins(date: String): Int {
        val min = date.substring(13, 16)
        return min.toInt()
    }

    private fun getHours(date: String): Int {
        //2010-02-05 10:25:14
        val hours = date.substring(11, 13)
        val realHours = hours.substring(2, 7)
        return realHours.toInt()
    }

    fun getAppUpTime(context: Context?): Long {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getLong(OPEN_TIME, System.currentTimeMillis())
    }

    fun checkDatePeriod(serverDate: Date): Boolean {
        try {
            val currentTime = Calendar.getInstance()
            val currentDate = currentTime.time
            //String idplus=  currentDate.toString();
            val serverTimeLong = serverDate.time
            val newServerDate = Date(serverTimeLong - 5 * 60000)
            val new2ServerDate = Date(serverTimeLong + 5 * 60000)
            return if (currentDate.after(new2ServerDate) || currentDate.before(newServerDate)) {
                false
            } else true
        } catch (e: Exception) {
        }
        return false
    }

    fun getFormattedUpTime(milliSec: Long): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = milliSec
        @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat("HH:mm:ss")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        return sdf.format(cal.time)
    }

    fun getTimerFormattedTime(milliSec: Long): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = milliSec
        @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat("mm:ss")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        return sdf.format(cal.time)
    }
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun getDateFromString(dateString: String?, dateFormat: String?, context: Context?): String? {
        val datePattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        // String datePattern = "yyyy-MM-dd";
        val sdf: DateFormat = SimpleDateFormat(datePattern, Locale.getDefault())
        val dateFormatter =
            SimpleDateFormat(dateFormat, Locale.forLanguageTag("en"))
        var date = Date()
        try {
            date = sdf.parse(dateString)
        } catch (e: ParseException) {
            Log.d(ContentValues.TAG, "bind: " + e.message)
        }
        return dateFormatter.format(Objects.requireNonNull(date))
    }

    fun getHoursAMinFromMSec(milliSec: Long): String {
        val c = Calendar.getInstance()
        c.timeInMillis = milliSec
        return c[Calendar.HOUR].toString() + ":" + c[Calendar.MINUTE]
    }

    fun getFormattedDateFromMilliSec(milliSec: Long?): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = milliSec!!
        @SuppressLint("SimpleDateFormat") val date = SimpleDateFormat("MMM dd  HH:mm")
        return date.format(cal.time)
    }

    fun getClearDayTimInMilliSec(milliSec: Long?): Long {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSec!!
        calendar[Calendar.HOUR_OF_DAY] = 0
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        return calendar.timeInMillis
    }

    fun getTimeInMAndS(milliSec: Long): String {
        val cal = Calendar.getInstance()
        cal.timeInMillis = milliSec
        @SuppressLint("SimpleDateFormat") val sdf = SimpleDateFormat("HH:mm")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        return sdf.format(cal.time)
    }

    fun getLongFromDate(date: String?): Long {
        var millis: Long? = null
        @SuppressLint("SimpleDateFormat") val simpleDateFormat = SimpleDateFormat()
        var date1 = Date()
        try {
            date1 = simpleDateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        millis = date1.time
        return millis
    }

    fun getDate(dateParamter: String?): Date? {
        var date: Date? = null
        //yyyy-MM-dd  HH:mm
        val format: DateFormat = SimpleDateFormat(dateParamter, Locale.ENGLISH)
        try {
            date = format.parse(dateParamter)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return date
    }

    fun getDayMonthYear(timesTamp: Long): String {
        val date = Date(timesTamp)
        val f: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.ENGLISH)
        //f.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
        return f.format(date)
    }
}