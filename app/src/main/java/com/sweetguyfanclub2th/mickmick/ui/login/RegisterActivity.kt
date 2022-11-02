package com.sweetguyfanclub2th.mickmick.ui.login

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.databinding.ActivityRegisterBinding
import com.sweetguyfanclub2th.mickmick.ui.main.MainActivity
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var auth: FirebaseAuth? = null
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.registercomplete.setOnClickListener {
            textInvisibleSet()

            if (checkEmail() && checkPasswd() && checkRepeatPasswd()) {
                createUser()
            } else {
                Toast.makeText(this, "각 형식을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun textInvisibleSet(){
        binding.emailCheckText.visibility = View.INVISIBLE
        binding.passwdCheckText.visibility = View.INVISIBLE
        binding.repeatPasswdCheckText.visibility = View.INVISIBLE
    }

    private fun checkEmail(): Boolean {
        val email = binding.registeremail.text.toString()
        val emailFormatCheck =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val p = Pattern.matches(emailFormatCheck, email)

        return when (p) {
            true -> true
            false -> {
                binding.emailCheckText.visibility = View.VISIBLE
                false
            }
        }
    }

    private fun checkPasswd(): Boolean {
        val passwd = binding.registerpasswd.text.toString()
        val passwdFormatCheck =
            "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&])[A-Za-z[0-9]\$@\$!%*#?&]{8,20}\$"
        val p = Pattern.matches(passwdFormatCheck, passwd)

        return when (p) {
            true -> true
            false -> {
                binding.passwdCheckText.visibility = View.VISIBLE
                false
            }
        }
    }

    private fun checkRepeatPasswd(): Boolean {
        val passwd = binding.registerpasswd.text.toString()
        val repeatPasswd = binding.registerRepeatPasswd.text.toString()
        val p = passwd == repeatPasswd

        when (p) {
            true -> return true
            false -> {
                binding.repeatPasswdCheckText.visibility = View.VISIBLE
                return false
            }
        }
    }

    private fun createUser() {
        val email = binding.registeremail.text.toString()
        val pw = binding.registerpasswd.text.toString()

        auth?.createUserWithEmailAndPassword(
            email, pw
        )
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Users Database

                    val userDataList = hashMapOf(
                        "user" to email,
                        "friends" to "",
                        "todoId" to "",
                    )

                    db.collection("users")
                        .document(email)
                        .set(userDataList)
                        .addOnSuccessListener { documentReference ->
                            Log.d(ContentValues.TAG, "유저 정보 업로드 및 회원가입에 성공하였습니다.")
                            moveMainPage(task.result?.user)
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "유저 정보 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        }

                } else if (task.exception?.message.isNullOrEmpty()) {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                } else {
                    signIn()
                }
            }
    }

    private fun signIn() {
        auth?.signInWithEmailAndPassword(
            binding.registeremail.text.toString(),
            binding.registerpasswd.text.toString()
        )
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "이메일/비밀번호 로그인에 성공하였습니다.")
                    moveMainPage(task.result?.user)
                } else {
                    Log.d(ContentValues.TAG, "이메일/비밀번호 로그인에 실패하였습니다.")
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}