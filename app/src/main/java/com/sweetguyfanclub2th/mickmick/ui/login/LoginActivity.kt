package com.sweetguyfanclub2th.mickmick.ui.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.util.Log
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
            when(loginCompleteCheck(binding.email.text, binding.passwd.text)){
                true -> signIn()
                false -> Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registerBtn.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

//    override fun onStart(){
//        super.onStart()
//        moveMainPage(auth?.currentUser)
//    }

    private fun loginCompleteCheck(emailText: Editable, passwdText: Editable): Boolean {
        val emailLength = emailText.length
        val passwdLength = passwdText.length

        return emailLength != 0 && passwdLength != 0
    }

    private fun signIn() {
        auth?.signInWithEmailAndPassword(
            binding.email.text.toString(),
            binding.passwd.text.toString()
        )
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

    private fun moveMainPage(user: FirebaseUser?){
        if(user != null) {
            Toast.makeText(this, "로그인이 완료되었습니다!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}