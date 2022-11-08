package com.sweetguyfanclub2th.mickmick.ui.main.map

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sweetguyfanclub2th.mickmick.databinding.FragmentMapBinding
import net.daum.mf.map.api.MapView
import net.daum.mf.map.api.MapPoint

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        val mapView = MapView(activity)
        val mapViewContainer = binding.mapView
        mapViewContainer.addView(mapView)
    }
}