package com.sweetguyfanclub2th.mickmick.ui.main.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sweetguyfanclub2th.mickmick.databinding.FragmentChangePassword2Binding

class ChangePasswordFragment2 : Fragment() {
    private var _binding: FragmentChangePassword2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangePassword2Binding.inflate(inflater, container, false)

        return binding.root
    }
}