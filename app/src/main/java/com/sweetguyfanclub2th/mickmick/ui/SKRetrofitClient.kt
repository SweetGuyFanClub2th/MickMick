package com.sweetguyfanclub2th.mickmick.ui

import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SKRetrofitClient {
    private const val URL = "https://apis.openapi.sk.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService() : SKRetrofitService {
        return retrofit.create(SKRetrofitService::class.java)
    }
}