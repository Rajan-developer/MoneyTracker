package com.nchl.moneytracker.presentation.model

import android.os.Parcel
import android.os.Parcelable


data class ExpenseCategory(
    val name: String? = "",
    val type: String? = "",
    val icon: ByteArray? = byteArrayOf(),
    val id: String = "",
) : Parcelable {


    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createByteArray() ?: byteArrayOf(),
        parcel.readString() ?: ""
    ) {
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ExpenseCategory

        if (name != other.name) return false
        if (type != other.type) return false
        if (!icon.contentEquals(other.icon)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + icon.contentHashCode()
        return result
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeByteArray(icon)
        parcel.writeString(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ExpenseCategory> {
        override fun createFromParcel(parcel: Parcel): ExpenseCategory {
            return ExpenseCategory(parcel)
        }

        override fun newArray(size: Int): Array<ExpenseCategory?> {
            return arrayOfNulls(size)
        }
    }

}