package com.sweetguyfanclub2th.mickmick.ui.main

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.ActivityMainBinding
import com.sweetguyfanclub2th.mickmick.ui.main.friend.FriendFragment
import com.sweetguyfanclub2th.mickmick.ui.main.home.HomeFragment
import com.sweetguyfanclub2th.mickmick.ui.main.setting.SettingFragment
import com.sweetguyfanclub2th.mickmick.ui.main.todo.TodoFragment
import java.security.MessageDigest

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var mBackWait : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getAppKeyHash()

        supportFragmentManager.beginTransaction().add(R.id.fragment, HomeFragment()).commit()

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, HomeFragment()).commit()
                }
                R.id.menu_friends -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, FriendFragment()).commit()
                }
                R.id.menu_todo -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, TodoFragment()).commit()
                }
                R.id.menu_settings -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, SettingFragment()).commit()
                }
            }

            true
        }
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

    override fun onBackPressed() {
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한 번 더 누르면 종료됩니다", Toast.LENGTH_SHORT).show()
        } else {
            finish()
        }
    }
}