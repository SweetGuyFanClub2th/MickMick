package com.sweetguyfanclub2th.mickmick.ui.main.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sweetguyfanclub2th.mickmick.databinding.ActivityChangePasswordBinding

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}