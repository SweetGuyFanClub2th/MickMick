package com.sweetguyfanclub2th.mickmick.ui.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sweetguyfanclub2th.mickmick.MainActivity
import com.sweetguyfanclub2th.mickmick.R

@SuppressLint("CustomSplashScreen")
class SplashActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startSomeNextActivity()
        finish()
    }

    private fun startSomeNextActivity() {
        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
    }
}