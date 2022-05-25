package com.example.android_gruppe_6.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.example.android_gruppe_6.domain.HarborData

@Entity(primaryKeys = ["harbor", "prognosis_len"])
data class DBHarborData constructor(
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

@Entity
data class DBHarbors constructor(
    @PrimaryKey val name: String,
    val apiName: String,
    val lat: String,
    val lon: String
)

data class HarborWithData(
    @Embedded val harbor: DBHarbors,
    @Relation(
        parentColumn = "name",
        entityColumn = "harbor"
    )
    val harborData: List<DBHarborData>
)
