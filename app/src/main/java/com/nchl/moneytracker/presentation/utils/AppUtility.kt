package com.nchl.moneytracker.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
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

    fun getCurrentDate(): String {
        val calendar = Calendar.getInstance()
        val year = calendar[Calendar.YEAR]
        val month = calendar[Calendar.MONTH] + 1
        val day = calendar[Calendar.DAY_OF_MONTH]
        return "$day/$month/$year"
    }


    fun getCurrentTime():String{
        val currentTime: LocalTime = LocalTime.now()
        return currentTime.toString()
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
}