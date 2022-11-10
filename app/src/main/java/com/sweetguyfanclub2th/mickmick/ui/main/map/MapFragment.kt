package com.sweetguyfanclub2th.mickmick.ui.main.map

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.skt.tmap.TMapView
import com.sweetguyfanclub2th.mickmick.data.searchpois.PoisResponse
import com.sweetguyfanclub2th.mickmick.databinding.FragmentMapBinding
import com.sweetguyfanclub2th.mickmick.ui.RetrofitClient
import com.sweetguyfanclub2th.mickmick.ui.RetrofitService
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val authToken = "l7xx7de642979fac440f8fad597ef2584f9e"
    companion object {
        private val retrofitClient: MapFragment = MapFragment()

        fun getInstance(): MapFragment {
            return retrofitClient
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        binding.searchButton.setOnClickListener {
            if(binding.mapSearch.text.isNullOrEmpty()){
                Toast.makeText(activity, "검색어를 입력하세요", Toast.LENGTH_SHORT).show()
            }

            else{
                val keyword = binding.mapSearch.text.toString()
                getDataToRetrofit(keyword)
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        val tmapview = TMapView(requireContext())
        tmapview.setSKTMapApiKey(authToken)
        binding.mapView.addView(tmapview)
    }

    private fun getDataToRetrofit(keyword : String){
        val retrofit = RetrofitClient.getApiService().getPois(1, keyword, authToken)

        retrofit.enqueue(object : Callback<PoisResponse>{
            override fun onFailure(call: Call<PoisResponse>, t: Throwable) {
                Log.d(TAG, t.message!!)
            }

            override fun onResponse(
                call: Call<PoisResponse>,
                response: Response<PoisResponse>
            ) {
                Log.d(TAG, "response : ${response.body()?.searchPoiInfo}") // 정상출력

                Log.d(TAG, "response (errorBody) : ${response.errorBody()}")
                Log.d(TAG, "response (message) : ${response.message()}")
                Log.d(TAG, "response (code) : ${response.code()}")
            }
        })
    }
}