package com.sweetguyfanclub2th.mickmick.ui.main.search.name.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sweetguyfanclub2th.mickmick.databinding.ActivityDetailNameBinding

class DetailNameActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailNameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNameBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}