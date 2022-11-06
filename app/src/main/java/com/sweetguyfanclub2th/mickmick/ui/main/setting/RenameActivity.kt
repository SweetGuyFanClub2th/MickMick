package com.sweetguyfanclub2th.mickmick.ui.main.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sweetguyfanclub2th.mickmick.databinding.ActivityRenameBinding

class RenameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRenameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRenameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backpress.setOnClickListener {
            finish()
        }
    }
}