package com.example.android_gruppe_6.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.android_gruppe_6.domain.HarborDataModel

@Entity
data class DatabaseHarborData constructor(
    val harbor: String,
    val year: String,
    val month: String,
    val day: String,
    val hour: String,
    @PrimaryKey val prognosis_len: String,
    val surge: String,
    val tide: String,
    val total: String,
    val p0: String,
    val p25: String,
    val p50: String,
    val p75: String,
    val p100: String
)

@Entity
data class DatabaseHarbor constructor(
    @PrimaryKey val harborName: String,
    val lat: String,
    val lon: String
)

data class HarborAndData(
    @Embedded val harbor: DatabaseHarbor,
    @Relation(
        parentColumn = "harborName",
        entityColumn = "harbor"
    )
    val harborData: DatabaseHarborData
)


fun List<DatabaseHarborData>.asDomainModel(): List<HarborDataModel> {
    return map {
        HarborDataModel(
            harbor = it.harbor,
            year = it.year,
            month = it.month,
            day = it.day,
            hour = it.hour,
            prognosis_len = it.prognosis_len,
            surge = it.surge,
            tide = it.tide,
            total = it.total,
            p0 = it.p0,
            p25 = it.p25,
            p50 = it.p50,
            p75 = it.p75,
            p100 = it.p100
        )
    }
}