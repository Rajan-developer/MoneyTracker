package com.nchl.moneytracker.presentation.utils.view

import android.content.Context
import android.os.Handler
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class TypeWriterTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    private var mText: CharSequence? = null
    private var mIndex = 0
    private var mDelay: Long = 150 // Adjust the delay here for typing speed

    private val mHandler = Handler()

    private val characterAdder = object : Runnable {
        override fun run() {
            if (mIndex <= mText?.length ?: 0) {
                val builder = SpannableStringBuilder(mText, 0, mIndex)
                text = builder
                mIndex++
                mHandler.postDelayed(this, mDelay)
            }
        }
    }

    fun animateText(text: CharSequence?) {
        mText = text
        mIndex = 0
        mHandler.removeCallbacks(characterAdder)
        mHandler.postDelayed(characterAdder, mDelay)
    }

    fun setCharacterDelay(millis: Long) {
        mDelay = millis
    }
}