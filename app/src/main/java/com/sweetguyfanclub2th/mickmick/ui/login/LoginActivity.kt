package com.sweetguyfanclub2th.mickmick.ui.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.sweetguyfanclub2th.mickmick.databinding.ActivityLoginBinding
import com.sweetguyfanclub2th.mickmick.ui.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        binding.loginBtn.setOnClickListener {
            when (loginCompleteCheck(binding.email, binding.passwd)) {
                true -> {
                    val email = binding.email.text.toString()
                    val passwd = binding.passwd.text.toString()
                    signIn(email, passwd)
                }

                false -> Toast.makeText(this, "빈 칸 없이 정보를 입력하세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    override fun onStart(){
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    private fun loginCompleteCheck(emailText: EditText?, passwdText: EditText?): Boolean {
        return !(emailText?.text.toString() == "" || passwdText?.text.toString() == ""
                || emailText?.text.isNullOrEmpty() || passwdText?.text.isNullOrEmpty())
    }

    private fun signIn(email : String, passwd : String) {
        auth?.signInWithEmailAndPassword(email, passwd)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithEmail:success")
                    moveMainPage(task.result?.user)
                } else {
                    Log.d(ContentValues.TAG, "failed")
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "로그인이 완료되었습니다!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}