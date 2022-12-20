package com.sweetguyfanclub2th.mickmick.ui.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.ActivityMainBinding
import com.sweetguyfanclub2th.mickmick.ui.main.friend.FriendFragment
import com.sweetguyfanclub2th.mickmick.ui.main.home.HomeFragment
import com.sweetguyfanclub2th.mickmick.ui.main.search.SearchPlaceFragment
import com.sweetguyfanclub2th.mickmick.ui.main.setting.SettingFragment
import com.sweetguyfanclub2th.mickmick.ui.main.todo.ScheduleFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val number = intent?.getStringExtra("message")
        Log.d("메인 액티비티", number.toString())

        if (number != null) {
            var fragment = ScheduleFragment()
            val bundle = Bundle()
            bundle.putString("message", number.toString())
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction().add(R.id.fragment, fragment).commit()
        }
        else {
            supportFragmentManager.beginTransaction().add(R.id.fragment, HomeFragment()).commit()
        }
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, HomeFragment()).commit()
                }
                R.id.menu_friends -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, FriendFragment()).commit()
                }
                R.id.menu_todo -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, ScheduleFragment()).commit()
                }
                R.id.menu_search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, SearchPlaceFragment()).commit()
                }
                R.id.menu_settings -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, SettingFragment()).commit()
                }
            }

            true
        }
    }

    private var backPressedTime : Long = 0
    override fun onBackPressed() {
        Log.d("TAG", "뒤로가기")

        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finish()
            return
        }

        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }
}