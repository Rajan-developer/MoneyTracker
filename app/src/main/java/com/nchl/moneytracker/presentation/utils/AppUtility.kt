package com.nchl.moneytracker.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import global.citytech.easydroid.core.extension.showToast
import java.io.ByteArrayOutputStream
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.O)
object AppUtility {

    fun isValidEmail(context: Context, email: String): Boolean {
        var isValidEmail = false;
        return if (email.isNullOrEmpty()) {
            context.showToast("email cannot be empty", Toast.LENGTH_SHORT)
            isValidEmail
        } else {
            val regex = Regex("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")
            if (!regex.matches(email)) {
                isValidEmail = false
                context.showToast("invalid email", Toast.LENGTH_SHORT)
            } else {
                isValidEmail = true
            }
            return isValidEmail
        }

    }

    fun isValidPhoneNumber(context: Context, phoneNumber: String): Boolean {
        var isValidPhoneNumber= false;
        return if (phoneNumber.isNullOrEmpty()) {
            context.showToast("Phone number cannot be empty", Toast.LENGTH_SHORT)
            isValidPhoneNumber
        } else {

            if (phoneNumber.length < 10 || phoneNumber.length > 10) {
                isValidPhoneNumber = false
                context.showToast("Phone number must be 10 digit", Toast.LENGTH_SHORT)
            } else {
                isValidPhoneNumber = true
            }
            return isValidPhoneNumber
        }

    }

    fun drawableToByteArray(context: Context, drawable: Drawable): ByteArray {
        val bitmap = drawableToBitmap(drawable)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

     fun getRandomColor(): Int {
        // Generate random RGB values for the color
        val r = Random.nextInt(256)
        val g = Random.nextInt(256)
        val b = Random.nextInt(256)

        // Combine RGB values into a single color integer
        return Color.rgb(r, g, b)
    }

    fun generateRandomDigits(length: Int): String {
        val random = java.util.Random()
        val sb = StringBuilder(length)
        repeat(length) {
            sb.append(random.nextInt(10)) // Append a random digit (0-9)
        }
        return sb.toString()
    }

    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        return "$day/$month/$year"
    }


    fun getCurrentTime(): String {
        return try {
            val currentTime: LocalTime = LocalTime.now()
            currentTime.toString()
        } catch (e: Exception) {
            e.printStackTrace() // Log the exception for debugging
            "Error occurred while getting current time" // Return a default value or error message
        }
    }

    fun convertDateToDisplayDate(date: String): String {
        var convertedDate = ""
        val inputDateFormat = SimpleDateFormat("d/M/yyyy", Locale.getDefault())
        // val outputDateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("MMM yyyy", Locale.ENGLISH)

        try {
            val inputDate = inputDateFormat.parse(date)
            val outputDateStr = outputDateFormat.format(inputDate)
            convertedDate = outputDateStr
        } catch (e: ParseException) {
            e.printStackTrace()
            convertedDate = dateFromDbToDisplayFormat(date,"MMMM yyyy")
        }
        return convertedDate
    }


    fun convertTimeToDisplayTime(time: String): String {
        var convertedTime = ""
        val inputTimeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val outputTimeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())

        try {
            val inputTime = inputTimeFormat.parse(time)
            val outputTimeStr = outputTimeFormat.format(inputTime)
            convertedTime = outputTimeStr
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return convertedTime;
    }


    fun dateStringToLong(dateString: String): Long {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault())
        try {
            val date = dateFormat.parse(dateString)
            return date?.time ?: 0L
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0L
    }

    fun stringToDate(dateString: String, format: String): Date {
        val formatter = SimpleDateFormat(format, Locale.getDefault())
        var date = formatter.parse(dateString)
        return date ?: Date()
    }

    fun dateFromDbToDisplayFormat(inputDateString: String, outputFormatter: String): String {
        val inputDateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat(outputFormatter, Locale.getDefault())

        try {
            val date = inputDateFormat.parse(inputDateString)
            return outputDateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }
}