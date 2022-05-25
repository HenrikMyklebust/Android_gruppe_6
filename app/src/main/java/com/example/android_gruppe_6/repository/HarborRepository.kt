package com.example.android_gruppe_6.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android_gruppe_6.database.*
import com.example.android_gruppe_6.domain.HarborData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HarborRepository(private val database: HarborsDatabase) {

    // Get data from specific harbor
    suspend fun getData(harborName: String): List<HarborWithData> {
        var response: List<HarborWithData>
        withContext(Dispatchers.IO){
            response = database.harborDao.getHarborWithData(harborName)
        }
        return response
    }

    suspend fun insertTestData() {
        withContext(Dispatchers.IO){
            val harborData = HarborData("Bergen", "2022", "5", "24", "12", "0", "0.24","-0.54", "-0.30", "0.19", "0.24", "0.24", "0.24", "0.29")
            val harborData2 = HarborData("Bergen", "2", "3", "4", "5", "7", "7","1", "2", "3", "4", "5", "6", "1")
            val harbor = DBHarbors("Bergen", "bergen",  "2", "3")
            val harborList = listOf(harborData,harborData2)

            database.harborDao.insertHarbor(harbor)
            database.harborDao.insertData(harborList.map {
                DBHarborData(
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
            })
        }
    }
}