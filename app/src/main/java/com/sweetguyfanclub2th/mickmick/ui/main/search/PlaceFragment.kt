package com.sweetguyfanclub2th.mickmick.ui.main.search

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.data.searchpois.Poi
import com.sweetguyfanclub2th.mickmick.data.searchpois.PoisResponse
import com.sweetguyfanclub2th.mickmick.databinding.FragmentSearchBinding
import com.sweetguyfanclub2th.mickmick.ui.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaceFragment : Fragment() {
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter : PlaceAdapter
    private lateinit var recyclerView: RecyclerView

    private val authToken = "l7xx7de642979fac440f8fad597ef2584f9e"
    companion object {
        private val retrofitClient: PlaceFragment = PlaceFragment()

        fun getInstance(): PlaceFragment {
            return retrofitClient
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)

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
                try{
                    val poiResult = response.body()?.searchPoiInfo?.pois?.poi!!
                    adapter = context?.let { PlaceAdapter(it, poiResult as ArrayList<Poi>) }!!

                    recyclerView = binding.searchRecycler
                    recyclerView.adapter = adapter
                } catch(e : Exception){
                    Toast.makeText(activity, "검색 결과가 없습니다", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}