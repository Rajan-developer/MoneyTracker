package com.nchl.moneytracker.presentation.model


data class ExpenseCategory(
    val name: String,
    val type: String,
    val icon: ByteArray,
) {


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

}