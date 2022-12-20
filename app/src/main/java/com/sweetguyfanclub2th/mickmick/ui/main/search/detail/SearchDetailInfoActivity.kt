package com.sweetguyfanclub2th.mickmick.ui.main.search.detail

import android.content.Intent
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
import com.sweetguyfanclub2th.mickmick.data.searchcongestion.CongestionResponse
import com.sweetguyfanclub2th.mickmick.databinding.ActivityDetailPlaceBinding
import com.sweetguyfanclub2th.mickmick.ui.SKRetrofitService
import com.sweetguyfanclub2th.mickmick.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchDetailInfoActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityDetailPlaceBinding
    private lateinit var id : String
    private lateinit var name : String
    private lateinit var fullAddressRoad : String
    private var lat : Double = 37.52673893
    private var lon : Double = 127.10845476

    private lateinit var confusion : String

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        val defaultPoint = LatLng(lat, lon)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom((defaultPoint), 16f))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.itemName.text = intent.getStringExtra("name").toString()
        val address = intent.getStringExtra("fullAddressRoad").toString()
        binding.itemFullAddressRoad.text = address

        lat = intent.getStringExtra("lat")?.toDouble()!!
        lon = intent.getStringExtra("lon")?.toDouble()!!

        id = intent.getStringExtra("id").toString()
        name = intent.getStringExtra("name").toString()
        fullAddressRoad = intent.getStringExtra("fullAddressRoad").toString()

        val retrofit = Retrofit.Builder().baseUrl("https://apis.openapi.sk.com/")
            .addConverterFactory(GsonConverterFactory.create()).build();
        val service = retrofit.create(SKRetrofitService::class.java);

        Log.d("TODO", id)

        service.getCongestion(
            "application/json",
            "l7xx7de642979fac440f8fad597ef2584f9e",
            id.toString()
        ).enqueue(object : Callback<CongestionResponse> {
            override fun onResponse(call: Call<CongestionResponse>, response: Response<CongestionResponse>) {
                if(response.isSuccessful){
                    val result: CongestionResponse? = response.body()
                    Log.d("TODO", "onResponse 성공 / " + result?.toString());
                }
                else{
                    val result: CongestionResponse? = response.body()
                    Log.d("TODO", "onResponse 실패 / " + result?.toString());
                }
            }

            override fun onFailure(call: Call<CongestionResponse>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString());
            }
        })

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map_view) as? SupportMapFragment
        mapFragment?.getMapAsync {
            it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lat, lon), 16f))
            it.setOnMapLoadedCallback{
                val location = LatLng(lat, lon)
                it.addMarker(
                    MarkerOptions()
                        .position(location)
                        .title(name)
                )
            }
        }

        binding.todoAdd.setOnClickListener {
            val intent = Intent(this@SearchDetailInfoActivity, MainActivity::class.java)
            intent.apply {
                this.putExtra("message", name + " (${address})") // 데이터 넣기
            }
            Log.d("서치프래그먼트", name.toString() + " (${address})")
            startActivity(intent)
            finish()
        }

        binding.backpress.setOnClickListener {
            finish()
        }
    }
}