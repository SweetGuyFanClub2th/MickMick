package com.sweetguyfanclub2th.mickmick.ui.main.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.skt.tmap.TMapView
import com.sweetguyfanclub2th.mickmick.databinding.FragmentMapBinding
import okhttp3.OkHttpClient
import okhttp3.Request

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val retrofitClient: MapFragment = MapFragment()

        fun getInstance(): MapFragment {
            return retrofitClient
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        tmapview.setSKTMapApiKey("l7xx7de642979fac440f8fad597ef2584f9e")
        binding.mapView.addView(tmapview)
    }
}