package com.example.android_gruppe_6.domain

import android.os.Parcelable
import com.example.android_gruppe_6.data_class.Harbour
import com.example.android_gruppe_6.database.DbHarbour
import kotlinx.parcelize.Parcelize

@Parcelize
data class HarborData(
    val harbor: String,
    val year: Int,
    val month: Int,
    val day: Int,
    val hour: Int,
    val prognosis_len: Int,
    val surge: Double,
    val tide: Double,
    val total: Double,
    val p0: Double,
    val p25: Double,
    val p50: Double,
    val p75: Double,
    val p100: Double
) : Parcelable

@Parcelize
data class Harbor(
    val name: String,
    val apiName: String,
    val lat: String,
    val lon: String
) : Parcelable

fun getHarbors(): List<Harbour> {
    return listOf(
        Harbour("Andenes Havn","andenes", 69.31428, 16.11939),
        Harbour("Bergen", "bergen",60.39299, 5.32415),
        Harbour("Bodø", "bodø", 67.28, 14.40501),
        Harbour("Hammerfest", "hammerfest", 70.66336, 23.68209),
        Harbour("Harstad", "harstad", 68.79833, 16.54165),
        Harbour("Heimsjø", "heimsjø", 63.4259, 9.0955),
        Harbour("Helgeroa", "helgeroa", 58.99412981506117, 9.856972656493534),
        Harbour("Honningsvåg", "honningsvåg", 70.98239581568338, 25.967423616711066),
        Harbour("Kabelvåg", "kabelvåg", 68.20732336980984, 14.487864442079388),
        Harbour("Kristiansund", "kristiansund", 63.10864653761978, 7.722474147193654),
        Harbour("Mausund", "mausund", 63.86821276375624, 8.66447655691493),
        Harbour("Måløy", "måløy", 61.93809665864493, 5.118614510545269),
        Harbour("Narvik", "narvik", 68.42940884384532, 17.42284892327571),
        Harbour("Ny-Ålesund", "ny-ålesund", 78.92259416633014, 11.918282910587184),
        Harbour("Oscarborg", "oscarsborg", 59.67546769024251, 10.605923203370356),
        Harbour("Oslo", "oslo", 59.90994356317572, 10.727094476390159),
        Harbour("Rørvik", "rørvik", 64.8616037781288, 11.237696460780763),
        Harbour("Stavanger", "stavanger", 58.972204256247934, 5.736736107353868),
        Harbour("Tregde", "tregde", 58.00936922094957, 7.557517822996417),
        Harbour("Tromsø", "tromsø", 69.646972696192, 18.957159841031118),
        Harbour("Trondheim", "trondheim", 63.433442627427965, 10.39079404834862),
        Harbour("Vardø", "vardø", 70.37026000495692, 31.112315955540918),
        Harbour("Viker", "viker", 59.96344795523004, 10.006934013816636),
        Harbour("Ålesund", "ålesund", 62.472588992155266, 6.140845535409749)
    )
}