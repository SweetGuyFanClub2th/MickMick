package com.sweetguyfanclub2th.mickmick.ui.main.search.name.detail

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.skt.tmap.TMapView
import com.sweetguyfanclub2th.mickmick.data.searchpois.Poi
import com.sweetguyfanclub2th.mickmick.databinding.ActivityDetailPlaceBinding


class DetailPlaceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailPlaceBinding
    private lateinit var poiData : Poi
    private val authToken = "l7xx7de642979fac440f8fad597ef2584f9e"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val tmapView = TMapView(this)
        tmapView.setSKTMapApiKey(authToken)
        binding.mapView.addView(tmapView)

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val fullAddressRoad = intent.getStringExtra("fullAddressRoad")
        val lat = intent.getStringExtra("lat")?.toDouble()
        val lon = intent.getStringExtra("lon")?.toDouble()

        Log.d(ContentValues.TAG, lat.toString())
        Log.d(ContentValues.TAG, lon.toString())

        if (lat != null && lon != null) {
            tmapView.setCenterPoint(lat.toDouble(), lon.toDouble(), false)
            tmapView.zoomLevel = 10
        } else {
            Toast.makeText(this, "검색이 잘못되었습니다", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.backpress.setOnClickListener {
            finish()
        }
    }
}