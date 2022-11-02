package com.sweetguyfanclub2th.mickmick.ui.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.ui.login.LoginActivity
import com.sweetguyfanclub2th.mickmick.ui.splash.intro.WelcomeActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences = getSharedPreferences("welcomeViewWatched", Activity.MODE_PRIVATE)
        val checkSharedPreferences = sharedPreferences.getBoolean("welcomeViewWatched", false)

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(Runnable {
            Log.d("checkValue", checkSharedPreferences.toString())
            when(checkSharedPreferences){
                false ->
                    moveWelcomeAndEditSharedPref(sharedPreferences)
                else ->
                    moveWelcomeAndEditSharedPref(sharedPreferences)
                    // just setting this to true for now
//                    moveLoginPage()
            }
        }, 3000)
    }

    private fun moveWelcomeAndEditSharedPref(sharedPreferences: SharedPreferences){
        val editBoolean = sharedPreferences.edit()
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