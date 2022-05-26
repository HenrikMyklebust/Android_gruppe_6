package com.example.android_gruppe_6.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface HarborService {
    @GET("1.1/")
    suspend fun getTideData(@Query("harbor") name: String): String
}

object TideNetworkGet {
    private const val BASE_URL = "http://10.239.120.166/weatherapi/tidalwater/"
    private val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val tideData: HarborService = retrofit.create(HarborService::class.java)
}