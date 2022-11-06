package com.sweetguyfanclub2th.mickmick.ui.main.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sweetguyfanclub2th.mickmick.databinding.FragmentSettingBinding
import com.sweetguyfanclub2th.mickmick.ui.main.MainActivity

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

//        binding.list1.setOnClickListener {
//            val intent = Intent(MainActivity(), renameActivity::class.java)
//            startActivity(intent)
//        }
//        binding.list2.setOnClickListener {
//            val intent = Intent(MainActivity(), changeIconAcvitity::class.java)
//            startActivity(intent)
//        }
//        binding.list3.setOnClickListener {
//            val intent = Intent(MainActivity(), changePasswordAcvitity::class.java)
//            startActivity(intent)
//        }

        return binding.root
    }
}