package com.example.android_gruppe_6.domain


data class HarborDataModel(
    val harbor: String,
    val year: String,
    val month: String,
    val day: String,
    val hour: String,
    val prognosis_len: String,
    val surge: String,
    val tide: String,
    val total: String,
    val p0: String,
    val p25: String,
    val p50: String,
    val p75: String,
    val p100: String
)