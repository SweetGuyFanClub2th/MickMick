package com.sweetguyfanclub2th.mickmick.ui

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitService {
    @GET("tmap/pois")
    fun getPois(
        @Query("version") version: Int = 1,
        @Query("searchKeyword") keyword: String,
        @Header("Authorization") authToken: String
    ): Call<CongestionResponse>
    
    @GET("puzzle/congestion/rltm/pois/{poiId}")
    fun getCongestion(
        @Header("Authorization") authToken: String
    ): Call<CongestionResponse>
}