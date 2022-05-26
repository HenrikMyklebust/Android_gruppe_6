package com.example.android_gruppe_6.repository

import com.example.android_gruppe_6.database.*
import com.example.android_gruppe_6.domain.TideData
import com.example.android_gruppe_6.domain.getHarbors
import com.example.android_gruppe_6.network.TideNetworkGet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.stream.IntStream.range

class TideRepository(private val database: HarborsDatabase) {

    // Download data on a single harbor
    suspend fun getApiTide(harborName: String): List<TideData> {
        var dataFromApi: String
        withContext(Dispatchers.IO) {
            dataFromApi = TideNetworkGet.tideData.getTideData(harborName)
        }
        val tideLines: MutableList<String> = dataFromApi.lines() as MutableList<String>
        val tideDataPreParse: MutableList<TideData> = mutableListOf()
        for (i in range(0, 9))
            tideLines.removeFirst()
        try {
            for (line in tideLines) {
                val splitLines: MutableList<String> = line.split(" ") as MutableList<String>
                splitLines.removeAll(listOf("", null))

                val tide = TideData(
                    harborName,
                    splitLines[0].toInt(),
                    splitLines[1].toInt(),
                    splitLines[2].toInt(),
                    splitLines[3].toInt(),
                    splitLines[4].toInt(),
                    splitLines[5].toDouble(),
                    splitLines[6].toDouble(),
                    splitLines[7].toDouble(),
                    splitLines[8].toDouble(),
                    splitLines[9].toDouble(),
                    splitLines[10].toDouble(),
                    splitLines[11].toDouble(),
                    splitLines[12].toDouble()
                )
                tideDataPreParse.add(tide)
            }
        }
        catch (e: Exception){}
        return tideDataPreParse
    }

    // Get data from specific harbor
    suspend fun getDataDB(harborName: String): List<HarborWithTide> {
        var response: List<HarborWithTide>
        withContext(Dispatchers.IO){
            response = database.harborDao.getHarborWithTide(harborName)
        }
        return response
    }

    // Insert data on tide
    suspend fun insertTides(tide: List<TideData>) {
        withContext(Dispatchers.IO){
            database.harborDao.insertTide(tide.map {
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

    // Insert full list of harbors
    suspend fun insertHarbors() {
        withContext(Dispatchers.IO){
            database.harborDao.insertHarbor(getHarbors().map { DbHarbour(
                name = it.name,
                apiName = it.apiName,
                lat = it.lat,
                lon = it.lon
            ) })
        }
    }
}