package com.sweetguyfanclub2th.mickmick.ui.main

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import com.sweetguyfanclub2th.mickmick.databinding.ActivityMainBinding
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAppKeyHash()

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

    // 앱 해시 키 얻는 코드
    fun getAppKeyHash() {
        try {
            val info =
                packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                var md: MessageDigest
                md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e("Hash key", something)
            }
        } catch (e: Exception) {
            Log.e("name not found", e.toString())
        }
    }
}