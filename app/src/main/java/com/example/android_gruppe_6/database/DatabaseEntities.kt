package com.example.android_gruppe_6.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(primaryKeys = ["harbor", "prognosis_len"])
data class DbTide constructor(
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
)

@Entity
data class DbHarbour constructor(
    val name: String,
    @PrimaryKey val apiName: String,
    val lat: Double,
    val lon: Double
)

data class HarborWithData(
    @Embedded val harbor: DbHarbour,
    @Relation(
        parentColumn = "name",
        entityColumn = "harbor"
    )
    val harborData: List<DbTide>
)
