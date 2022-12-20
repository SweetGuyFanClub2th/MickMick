package com.sweetguyfanclub2th.mickmick.ui

import com.sweetguyfanclub2th.mickmick.data.searchcongestion.CongestionResponse
import com.sweetguyfanclub2th.mickmick.data.searchpois.PoisResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface SKRetrofitService {
    @GET("tmap/pois")
    fun getPois(
        @Query("version", encoded = true) version: Int,
        @Query("searchKeyword", encoded = false) keyword: String,
        @Header("appKey") authToken: String
    ): Call<PoisResponse>
    
    @GET("puzzle/congestion/rltm/pois/{poiId}")
    fun getCongestion(
        @Header("appKey") authToken: String,
        @Path("poiId") poiId : String
    ): Call<CongestionResponse>
}