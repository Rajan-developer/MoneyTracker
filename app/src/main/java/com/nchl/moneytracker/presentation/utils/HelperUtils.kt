package com.nchl.moneytracker.presentation.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.security.SecureRandom
import java.util.*


object HelperUtils {
    fun isBlankOrNull(str: String?): Boolean {
        var strLen: Int = 0
        return if (str != null && str.length.also { strLen = it } != 0) {
            for (i in 0 until strLen) {
                if (!Character.isWhitespace(str[i])) {
                    return false
                }
            }
            true
        } else {
            true
        }
    }

    fun generateMaxValueOfLength(length: Int): Long {
        return if (length <= 0) {
            0L
        } else {
            val stringBuilder = StringBuilder()
            for (i in 0 until length) {
                stringBuilder.append(9)
            }
            java.lang.Long.valueOf(stringBuilder.toString())
        }
    }

    @Synchronized
    fun generateNumericKey(): String {
        val secureRandom = SecureRandom()
        return secureRandom.nextLong().toString()
    }

    @get:Synchronized
    val timeAsNanoSecond: Long
        get() = System.nanoTime()

    @Synchronized
    fun generateUUID(): String {
        return UUID.randomUUID().toString().replace("-".toRegex(), "")
    }

    fun getStackTrace(e: Throwable): String {
        val sb = StringBuilder()
        val var2 = e.stackTrace
        val var3 = var2.size
        for (var4 in 0 until var3) {
            val element = var2[var4]
            sb.append(element.toString())
            sb.append("\n")
        }
        return sb.toString()
    }

    fun joining(list: List<*>?, joining: String?): String? {
        var joining = joining
        return if (list == null) {
            null
        } else {
            if (isBlankOrNull(joining)) {
                joining = ","
            }
            val stringBuilder = StringBuilder()
            val var3 = list.iterator()
            while (var3.hasNext()) {
                val obj = var3.next()!!
                stringBuilder.append(obj)
            }
            stringBuilder.toString()
        }
    }

    fun getStringOrEmpty(str: String?): String {
        return getStringOrDefault(str, "")
    }

    fun getStringOrDefault(str: String?, defaultVal: String): String {
        return str ?: defaultVal
    }

    fun getImageByteArray(
        filePath: String?,
        compressFormat: Bitmap.CompressFormat?,
        quality: Int
    ): ByteArray {
        return if (isBlankOrNull(filePath)) {
            throw IllegalArgumentException("pathName should not be null or empty")
        } else {
            val bitmap = BitmapFactory.decodeFile(filePath)
            if (bitmap == null) {
                throw IllegalArgumentException("Could not decode the given filePath. Invalid filePath for decoding into Bitmap")
            } else {
                val arrayOutputStream = ByteArrayOutputStream()
                if (compressFormat != null) {
                    bitmap.compress(compressFormat, quality, arrayOutputStream)
                }
                arrayOutputStream.toByteArray()
            }
        }
    }

    fun getImageBitmap(data: ByteArray?): Bitmap {
        var bitmap: Bitmap? = null
        return if (data == null) {
            throw IllegalArgumentException("data should not be null")
        } else {
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
            bitmap
                ?: throw IllegalArgumentException("Could not decode the given data. Invalid data for decoding into Bitmap")
        }
    }

    fun String?.isNullOrEmptyOrBlank(): Boolean {
        var boolean = true
        if ((this != null) && !(this.isEmpty() || this.isBlank())) {
            boolean = false
        }
        return boolean;
    }

    fun String?.defaultOrEmptyValue(): String {
        var defaultValue = AppConstant.EMPTY_STRING
        try {
            if (!this.isNullOrEmptyOrBlank()) {
                defaultValue = this!!
            }
        } catch (ex: Exception) {
            AppLogger.log("Exception ::: ".plus(ex.message))
        }
        return defaultValue
    }
}