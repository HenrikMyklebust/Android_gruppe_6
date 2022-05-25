package com.example.android_gruppe_6.network

import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "http://10.239.120.166/weatherapi/tidalwater/1.1/?harbor="
val retrofit: Retrofit = Retrofit.Builder().baseUrl(BASE_URL).build()

interface HarborService {
    @GET(":harbor")
    suspend fun getHarborData(harbor: String): String
}

object HarborNetworkGet {
    val harborData: HarborService = retrofit.create(HarborService::class.java)
}