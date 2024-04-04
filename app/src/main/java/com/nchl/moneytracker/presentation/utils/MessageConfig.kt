package com.nchl.moneytracker.presentation.utils

import android.view.View
import com.nchl.moneytracker.presentation.utils.HelperUtils.isBlankOrNull



class MessageConfig private constructor(builder: Builder) {
     val title: String?
     val message: String?
     val positiveLabel: String?
     val negativeLabel: String?
     val neutralLabel: String?
     val onPositiveClick: View.OnClickListener?
     val onNegativeClick: View.OnClickListener?
     val onNeutralClick: View.OnClickListener?

    init {
        title = builder.title
        message = builder.message
        positiveLabel = builder.positiveLabel
        negativeLabel = builder.negativeLabel
        neutralLabel = builder.neutralLabel
        onPositiveClick = builder.onPositiveClick
        onNegativeClick = builder.onNegativeClick
        onNeutralClick = builder.onNeutralClick
    }

//    fun getTitle(): String? {
//        return title
//    }
//
//    fun getMessage(): String? {
//        return message
//    }
//
//    fun getPositiveLabel(): String? {
//        return positiveLabel
//    }
//
//    fun getNegativeLabel(): String {
//        return if (HelperUtils.isBlankOrNull(negativeLabel)) "NO" else negativeLabel!!
//    }
//
//    fun getNeutralLabel(): String? {
//        return neutralLabel
//    }
//
//    fun getOnPositiveClick(): View.OnClickListener? {
//        return onPositiveClick
//    }
//
//    fun getOnNegativeClick(): View.OnClickListener? {
//        return onNegativeClick
//    }
//
//    fun getOnNeutralClick(): View.OnClickListener? {
//        return onNeutralClick
//    }
//
//    fun hasNegativeButton(): Boolean {
//        return if (!HelperUtils.isBlankOrNull(negativeLabel)) {
//            true
//        } else {
//            onNegativeClick != null
//        }
//    }

    class Builder {
        var title: String? = null
        var message: String? = null
        var positiveLabel: String? = null
        var negativeLabel: String? = null
        var neutralLabel: String? = null
        var onPositiveClick: View.OnClickListener? = null
        var onNegativeClick: View.OnClickListener? = null
        var onNeutralClick: View.OnClickListener? = null

        fun title(title: String?): Builder {
            this.title = title
            return this
        }

        fun message(message: String?): Builder {
            this.message = message
            return this
        }

        fun positiveLabel(positiveLabel: String?): Builder {
            this.positiveLabel = positiveLabel
            return this
        }

        fun negativeLabel(negativeLabel: String?): Builder {
            this.negativeLabel = negativeLabel
            return this
        }

        fun neutralLabel(neutralLabel: String?): Builder {
            this.neutralLabel = neutralLabel
            return this
        }

        fun onPositiveClick(onPositiveClick: View.OnClickListener?): Builder {
            this.onPositiveClick = onPositiveClick
            return this
        }

        fun onNegativeClick(onNegativeClick: View.OnClickListener?): Builder {
            this.onNegativeClick = onNegativeClick
            return this
        }

        fun onNeutralClick(onNeutralClick: View.OnClickListener?): Builder {
            this.onNeutralClick = onNeutralClick
            return this
        }

        fun build(): MessageConfig {
            return MessageConfig(this)
        }
    }
}


//
//class MessageConfig private constructor(builder: Builder) {
//     val title: String?
//     val message: String?
//     val positiveLabel: String?
//     val negativeLabel: String?
//     val neutralLabel: String?
//     val onPositiveClick: View.OnClickListener?
//     val onNegativeClick: View.OnClickListener?
//     val onNeutralClick: View.OnClickListener?
//
//    init {
//        title = builder.title
//        message = builder.message
//        positiveLabel = builder.positiveLabel
//        negativeLabel = builder.negativeLabel
//        neutralLabel = builder.neutralLabel
//        onPositiveClick = builder.onPositiveClick
//        onNegativeClick = builder.onNegativeClick
//        onNeutralClick = builder.onNeutralClick
//    }
//
//    fun getNegativeLabel(): String {
//        return if (isBlankOrNull(negativeLabel)) "NO" else negativeLabel!!
//    }
//
//    fun hasNegativeButton(): Boolean {
//        return if (!isBlankOrNull(negativeLabel)) {
//            true
//        } else {
//            onNegativeClick != null
//        }
//    }
//
//    class Builder {
//        var title: String? = null
//        var message: String? = null
//        var positiveLabel: String? = null
//        var negativeLabel: String? = null
//        var neutralLabel: String? = null
//        var onPositiveClick: View.OnClickListener? = null
//        var onNegativeClick: View.OnClickListener? = null
//        var onNeutralClick: View.OnClickListener? = null
//        fun title(title: String?): Builder {
//            this.title = title
//            return this
//        }
//
//        fun message(message: String?): Builder {
//            this.message = message
//            return this
//        }
//
//        fun positiveLabel(positiveLabel: String?): Builder {
//            this.positiveLabel = positiveLabel
//            return this
//        }
//
//        fun negativeLabel(negativeLabel: String?): Builder {
//            this.negativeLabel = negativeLabel
//            return this
//        }
//
//        fun neutralLabel(neutralLabel: String?): Builder {
//            this.neutralLabel = neutralLabel
//            return this
//        }
//
//        fun onPositiveClick(onPositiveClick: View.OnClickListener?): Builder {
//            this.onPositiveClick = onPositiveClick
//            return this
//        }
//
//        fun onNegativeClick(onNegativeClick: View.OnClickListener?): Builder {
//            this.onNegativeClick = onNegativeClick
//            return this
//        }
//
//        fun onNeutralClick(onNeutralClick: View.OnClickListener?): Builder {
//            this.onNeutralClick = onNeutralClick
//            return this
//        }
//
//        fun build(): MessageConfig {
//            return MessageConfig(this)
//        }
//    }
//}