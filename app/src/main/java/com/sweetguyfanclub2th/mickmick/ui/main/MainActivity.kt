package com.sweetguyfanclub2th.mickmick.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sweetguyfanclub2th.mickmick.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        supportFragmentManager.beginTransaction().add(R.id.fragment, MainFragment()).commit()
//
//        binding.bottomMenu.setOnItemSelectedListener {
//            when(it.itemId) {
//                R.id.menu_consent -> {
//                    supportFragmentManager.beginTransaction().replace(R.id.layout_consent, ConsentFragment()).commit()
//                }
//                R.id.menu_input -> {
//                    supportFragmentManager.beginTransaction().replace(R.id.layout_info_input, InfoInputFragment()).commit()
//                }
//            }
//
//            true
//        }
    }
}