package com.sweetguyfanclub2th.mickmick.ui.main.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.databinding.ActivityRenameBinding

class RenameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRenameBinding
    private var auth: FirebaseAuth? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var beforeName: String
    private lateinit var beforeNickname: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRenameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backpress.setOnClickListener {
            finish()
        }
        var database = FirebaseFirestore.getInstance()

        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            binding.registerEmail.hint = firebaseUser.email.toString()
        }
        else {
            binding.registerEmail.hint = "유저 정보가 잘못되었습니다."
        }

        binding.editComplete.setOnClickListener {

        }
    }
}