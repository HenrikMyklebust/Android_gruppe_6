package com.example.android_gruppe_6.data_class

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tide(
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val progNr: Int,
    val surge: Double,
    val tide: Double,
    val total: Double,
    val prob0: Double,
    val prob25: Double,
    val prob50: Double,
    val prob75: Double,
    val prob100: Double
): Parcelable
