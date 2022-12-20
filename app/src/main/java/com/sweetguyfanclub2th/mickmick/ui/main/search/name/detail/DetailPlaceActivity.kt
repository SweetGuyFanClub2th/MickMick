package com.sweetguyfanclub2th.mickmick.ui.main.search.name.detail

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


class DetailPlaceActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityDetailPlaceBinding
    private lateinit var id : String
    private lateinit var name : String
    private lateinit var fullAddressRoad : String
    private var lat : Double = 37.52673893
    private var lon : Double = 127.10845476

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        val sydney = LatLng(-33.852, 151.211)
        googleMap.addMarker(
            MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemName.text = intent.getStringExtra("name").toString()
        binding.itemFullAddressRoad.text = intent.getStringExtra("fullAddressRoad").toString()

        lat = intent.getStringExtra("lat")?.toDouble()!!
        lon = intent.getStringExtra("lon")?.toDouble()!!

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_view) as? SupportMapFragment
        mapFragment?.getMapAsync {
            it.setOnMapLoadedCallback{
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 16f))
            }
        }

        id = intent.getStringExtra("id").toString()
        name = intent.getStringExtra("name").toString()
        fullAddressRoad = intent.getStringExtra("fullAddressRoad").toString()


        binding.backpress.setOnClickListener {
            finish()
        }
    }
}