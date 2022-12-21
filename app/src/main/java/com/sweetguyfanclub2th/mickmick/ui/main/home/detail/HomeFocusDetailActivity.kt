package com.sweetguyfanclub2th.mickmick.ui.main.home.detail

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.searchinfo.PoiInfo
import com.sweetguyfanclub2th.mickmick.databinding.ActivityDetailHomeFocusBinding
import com.sweetguyfanclub2th.mickmick.ui.SKRetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFocusDetailActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityDetailHomeFocusBinding
    private lateinit var id: String
    private lateinit var name: String
    private lateinit var fullAddressRoad: String
    private var lat: Double = 37.52673893
    private var lon: Double = 127.10845476

    private val appKey = "l7xx7de642979fac440f8fad597ef2584f9e"

    companion object {
        private val retrofitClient: HomeFocusDetailActivity = HomeFocusDetailActivity()

        fun getInstance(): HomeFocusDetailActivity {
            return retrofitClient
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 16f))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHomeFocusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getStringExtra("poi").toString()
        getPoiDataToRetrofit(id)

        binding.time.text = intent.getStringExtra("time").toString()
        binding.player.text = intent.getStringExtra("member").toString()
        binding.itemName.text = intent.getStringExtra("title").toString()

        binding.backpress.setOnClickListener {
            finish()
        }
    }

    private fun getPoiDataToRetrofit(poi: String) {
        val poiRetrofit = SKRetrofitClient.getApiService().getPoiInfo(
            poi, 1, "id", "WGS84GEO", appKey
        )

        poiRetrofit.enqueue(object : Callback<PoiInfo> {
            override fun onFailure(call: Call<PoiInfo>, t: Throwable) {
                Log.d(ContentValues.TAG, t.message!!)
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<PoiInfo>,
                response: Response<PoiInfo>
            ) {
                val poiResult = response.body()?.poiDetailInfo
                if (poiResult != null) {
                    id = poiResult.id
                    name = poiResult.name
                    fullAddressRoad = poiResult.address
                    binding.place.text = "$name ($fullAddressRoad)"
                    lat = poiResult.lat.toDouble()
                    lon = poiResult.lon.toDouble()

                    mapSetting()
                }
            }
        })
    }

    private fun mapSetting() {
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.map_view) as? SupportMapFragment
        mapFragment?.getMapAsync {
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 16f))
            it.setOnMapLoadedCallback {
                val location = LatLng(lat, lon)
                it.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title(name)
                )
            }
        }
    }
}