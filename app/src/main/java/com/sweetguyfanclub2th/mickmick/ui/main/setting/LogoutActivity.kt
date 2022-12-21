package com.sweetguyfanclub2th.mickmick.ui.main.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sweetguyfanclub2th.mickmick.databinding.ActivityLogoutBinding
import com.sweetguyfanclub2th.mickmick.ui.splash.SplashActivity

class LogoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogoutBinding
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val auth = Firebase.auth

        binding.editComplete.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.backpress.setOnClickListener {
            finish()
        }
    }
}