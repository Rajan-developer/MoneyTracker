package com.nchl.moneytracker.domain.model


data class DatabaseReponse(
    val result: DataBaseResult,
    val message: String
)

enum class DataBaseResult(
    val code: Int,
    val message: String
) {
    NONE(0, "None"),
    FAILURE(-1, "Failure"),
    SUCCESS(1, "Success");
}