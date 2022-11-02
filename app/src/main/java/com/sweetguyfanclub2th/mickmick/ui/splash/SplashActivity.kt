package com.sweetguyfanclub2th.mickmick.ui.splash

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        var SharedPreferences = getSharedPreferences("welcomeViewWatched", Activity.MODE_PRIVATE)
        var checkSharedPreferences = SharedPreferences.getBoolean("welcomeViewWatched", false)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable {
            when(checkSharedPreferences){
                false -> moveWelcomeAndEditSharedPref(SharedPreferences)
                else -> moveLoginPage()
            }
        }, 3000)
    }

    private fun moveWelcomeAndEditSharedPref(SharedPreferences : SharedPreferences){
        val editBoolean = SharedPreferences.edit()
        editBoolean.putBoolean("welcomeViewWatched", true)
        editBoolean.apply()
        startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
    }

    private fun moveLoginPage(){
        Intent(this, LoginActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}