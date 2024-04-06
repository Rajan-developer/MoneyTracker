package com.nchl.moneytracker.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class ExpenseTransaction(
    var categoryId: String? = "",
    var categoryName: String? = "",
    var categoryType: String? = "",
    var description: String? = "",
    var transactionAmount: String? = "",
    var date: String? = "",
    var time: String? = "",
    var id: String = "",
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(categoryId)
        parcel.writeString(categoryName)
        parcel.writeString(categoryType)
        parcel.writeString(description)
        parcel.writeString(transactionAmount)
        parcel.writeString(date)
        parcel.writeString(time)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExpenseTransaction> {
        override fun createFromParcel(parcel: Parcel): ExpenseTransaction {
            return ExpenseTransaction(parcel)
        }

        override fun newArray(size: Int): Array<ExpenseTransaction?> {
            return arrayOfNulls(size)
        }
    }
}