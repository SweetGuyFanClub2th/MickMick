package com.sweetguyfanclub2th.mickmick.ui

import com.sweetguyfanclub2th.mickmick.data.searchcongestion.CongestionResponse
import com.sweetguyfanclub2th.mickmick.data.searchinfo.PoiInfo
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
        @Header("accept") acceptType: String,
        @Header("appkey") authToken: String,
        @Path("poiId") poiId : String
    ): Call<CongestionResponse>

    @GET("tmap/pois/{poiInfo}")
    fun getPoiInfo(
        @Path("poiInfo") poiInfo : String,
        @Query("version", encoded = true) version: Int,
        @Query("findOption", encoded = true) findOption : String,
        @Query("resCoordType", encoded = true) resCoordType : String,
        @Header("appkey") authToken: String
    ): Call<PoiInfo>
}