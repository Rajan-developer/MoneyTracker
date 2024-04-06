package com.nchl.moneytracker.presentation.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.Toast
import global.citytech.easydroid.core.extension.showToast
import java.io.ByteArrayOutputStream

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
        // Convert drawable resource to Bitmap
        //val bitmap = BitmapFactory.decodeResource(context.resources, drawableId)

        // Convert Bitmap to byte array
//        val outputStream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
//        return outputStream.toByteArray()


        // Convert drawable to Bitmap
        val bitmap = drawableToBitmap(drawable)

        // Convert Bitmap to byte array
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    fun drawableToBitmap(drawable: Drawable): Bitmap {
        // If the drawable is already a bitmap, return it
        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        // Otherwise, create a new bitmap and draw the drawable onto it
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
}