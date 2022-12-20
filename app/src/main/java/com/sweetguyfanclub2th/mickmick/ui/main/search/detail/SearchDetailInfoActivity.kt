package com.sweetguyfanclub2th.mickmick.ui.main.search.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.ActivityDetailPlaceBinding


class SearchDetailInfoActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityDetailPlaceBinding
    private lateinit var id : String
    private lateinit var name : String
    private lateinit var fullAddressRoad : String
    private var lat : Double = 37.52673893
    private var lon : Double = 127.10845476

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.moveCamera(com.google.android.gms.maps.CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 16f))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemName.text = intent.getStringExtra("name").toString()
        binding.itemFullAddressRoad.text = intent.getStringExtra("fullAddressRoad").toString()

        lat = intent.getStringExtra("lat")?.toDouble()!!
        lon = intent.getStringExtra("lon")?.toDouble()!!

        id = intent.getStringExtra("id").toString()
        name = intent.getStringExtra("name").toString()
        fullAddressRoad = intent.getStringExtra("fullAddressRoad").toString()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_view) as? SupportMapFragment
        mapFragment?.getMapAsync {
            it.setOnMapLoadedCallback{
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 16f))

                val location = LatLng(lat, lon)
                it.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title(name)
                )
            }
        }

        binding.todoAdd.setOnClickListener {

        }

        binding.backpress.setOnClickListener {
            finish()
        }
    }
}