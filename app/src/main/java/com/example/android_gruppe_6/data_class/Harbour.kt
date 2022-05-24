package com.example.android_gruppe_6.data_class

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Harbour(
    val name: String,
    val forApi: String,
    val lat: Double,
    val long: Double
): Parcelable