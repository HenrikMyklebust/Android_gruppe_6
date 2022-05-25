package com.example.android_gruppe_6.repository

import com.example.android_gruppe_6.data_class.Tide
import com.example.android_gruppe_6.database.*
import com.example.android_gruppe_6.domain.HarborData
import com.example.android_gruppe_6.domain.getHarbours
import com.example.android_gruppe_6.network.TideNetworkGet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.stream.IntStream.range

class TideRepository(private val database: HarborsDatabase) {

    suspend fun getDataNetwork(harborName: String): String {
        var tideData: String
        withContext(Dispatchers.IO) {
            tideData = TideNetworkGet.tideData.getTideData(harborName)
        }
        val tideLines: MutableList<String> = tideData.lines() as MutableList<String>

        for (i in range(0, 9))
            tideLines.removeFirst()
        for (i in tideLines) {
            val split: MutableList<String> = i.split(" ") as MutableList<String>
            for (j in split){
                //if (j.isBlank())
                //    split.removeAt()
            }
        }
        return tideData
    }


    fun insertTide(tide: Tide) {

    }

    // Get data from specific harbor
    suspend fun getDataDB(harborName: String): List<HarborWithData> {
        var response: List<HarborWithData>
        withContext(Dispatchers.IO){
            response = database.harborDao.getHarborWithData(harborName)
        }
        return response
    }

    suspend fun insertTestData() {
        withContext(Dispatchers.IO){
            val harborData = HarborData("Bergen", 2022, 5, 24, 12, 0, 0.24,-0.54, -0.30, 0.19, 0.24, 0.24, 0.24, 0.29)
            val harborData2 = HarborData("Bergen", 2022, 5, 24, 13, 1, 0.24,-0.54, -0.30, 0.19, 0.24, 0.24, 0.24, 0.29)
            val harbours = getHarbours()
            val harborList = listOf(harborData,harborData2)

            database.harborDao.insertHarbor(harbours.map { DbHarbour(
                name = it.name,
                apiName = it.forApi,
                lat = it.lat,
                lon = it.long
            ) })

            database.harborDao.insertData(harborList.map {
                DbTide(
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