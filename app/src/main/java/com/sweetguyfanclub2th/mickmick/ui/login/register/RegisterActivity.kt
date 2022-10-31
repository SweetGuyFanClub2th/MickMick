package com.sweetguyfanclub2th.mickmick.ui.login.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.ActivityLoginBinding
import com.sweetguyfanclub2th.mickmick.databinding.ActivityRegisterBinding
import com.sweetguyfanclub2th.mickmick.ui.login.register.navi.ConsentFragment
import com.sweetguyfanclub2th.mickmick.ui.login.register.navi.InfoInputFragment

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().add(R.id.fragment, ConsentFragment()).commit()

        binding.bottomMenu.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_consent -> {
                    supportFragmentManager.beginTransaction().replace(R.id.layout_consent, ConsentFragment()).commit()
                }
                R.id.menu_input -> {
                    supportFragmentManager.beginTransaction().replace(R.id.layout_info_input, InfoInputFragment()).commit()
                }
            }

            true
        }
    }
}