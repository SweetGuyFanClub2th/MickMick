package com.sweetguyfanclub2th.mickmick.ui.main.search.name.detail

import android.R
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.skt.tmap.TMapView
import com.sweetguyfanclub2th.mickmick.data.searchpois.PoisResponse
import com.sweetguyfanclub2th.mickmick.databinding.ActivityDetailNameBinding
import com.sweetguyfanclub2th.mickmick.ui.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetailNameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailNameBinding
    private val authToken = "l7xx7de642979fac440f8fad597ef2584f9e"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tMapView = TMapView(this)
        tMapView.setSKTMapApiKey(authToken)
        binding.mapView.addView(tMapView)

        binding.backpress.setOnClickListener {
            finish()
        }
    }
}