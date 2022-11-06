package com.sweetguyfanclub2th.mickmick.ui.main.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sweetguyfanclub2th.mickmick.databinding.ActivityChangeIconBinding

class changeIconAcvitity : AppCompatActivity() {
    private lateinit var binding: ActivityChangeIconBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangeIconBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}