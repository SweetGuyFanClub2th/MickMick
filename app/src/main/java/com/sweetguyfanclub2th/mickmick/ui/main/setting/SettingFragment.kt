package com.sweetguyfanclub2th.mickmick.ui.main.setting

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.sweetguyfanclub2th.mickmick.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        binding.apply {
            list1.setOnClickListener {
                val intent = Intent(activity, RenameActivity::class.java)
                startActivity(intent)
            }
            list2.setOnClickListener {
                val intent = Intent(activity, ChangeIconActivity::class.java)
                startActivity(intent)
            }
            list3.setOnClickListener {
                val intent = Intent(activity, ChangePasswordActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }
}