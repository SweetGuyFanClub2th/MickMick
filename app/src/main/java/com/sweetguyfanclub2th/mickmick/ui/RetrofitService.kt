package com.sweetguyfanclub2th.mickmick.ui

import com.sweetguyfanclub2th.mickmick.data.searchpois.PoisResponse
import com.sweetguyfanclub2th.mickmick.searchcongestion.CongestionResponse
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
    ): Call<PoisResponse>
    
    @GET("puzzle/congestion/rltm/pois/{poiId}")
    fun getCongestion(
        @Header("Authorization") authToken: String
    ): Call<CongestionResponse>
}