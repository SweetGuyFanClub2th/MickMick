package com.sweetguyfanclub2th.mickmick.ui

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val URL = "https://apis.openapi.sk.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getApiService() : RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }
}