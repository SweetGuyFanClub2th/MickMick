package com.sweetguyfanclub2th.mickmick.ui.main.search.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.internal.service.Common.API
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


class ShowDetailInfoActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityDetailPlaceBinding
    private lateinit var id : String
    private lateinit var name : String
    private lateinit var fullAddressRoad : String
    private var lat : Double = 37.52673893
    private var lon : Double = 127.10845476

    private lateinit var confusion : String
    private lateinit var confusionColor : String

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

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.openapi.sk.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(SKRetrofitService::class.java)
        val callConfusion = api.getCongestion(
            "application/json",
            "l7xx7de642979fac440f8fad597ef2584f9e",
            id
        )

        callConfusion.enqueue(object : Callback<CongestionResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<CongestionResponse>,
                response: Response<CongestionResponse>
            ) {
                Log.d("todoP", "성공 : ${response.raw()}")
                try {
                    Log.d("todoP", response.body().toString().split("code=")[1].split(",")[0])
                    val confusionScore = response.body().toString().split("congestionLevel=")[1].split(",")[0]
                    Log.d("todoP", "성공 : $confusionScore")

                    when(confusionScore.toInt()) {
                        1, 2 -> {
                            confusion = "여유로운 "
                            confusionColor = "#FF5D7763"
                        }
                        3, 4, 5, 6 -> {
                            confusion = "보통인"
                            confusionColor = "#FF406695"
                        }
                        7, 8 -> {
                            confusion = "혼잡한"
                            confusionColor = "#FF8C7519"
                        }
                        9, 10 -> {
                            confusion = "매우 혼잡한"
                            confusionColor = "#FF94493F"
                        }
                    }
                }
                catch (e: Exception) {
                    confusion = "자료 없음"
                    confusionColor = "#FF5F5F5F"
                }
                if (confusion == "자료 없음") {
                    binding.congestion.text = "혼잡도 자료가 없습니다."
                }
                else {
                    binding.congestion.text = "현재 $confusion 상황입니다."
                }
                binding.congestion.setTextColor(Color.parseColor(confusionColor))
            }

            override fun onFailure(call: Call<CongestionResponse>, t: Throwable) {
                Log.d("todoP", "실패 : $t")
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
            val intent = Intent(this@ShowDetailInfoActivity, MainActivity::class.java)
            intent.apply {
                this.putExtra("message", name) // 데이터 넣기
                this.putExtra("poi", id)
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