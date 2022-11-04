package com.sweetguyfanclub2th.mickmick.ui.login

import android.annotation.SuppressLint
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
import com.sweetguyfanclub2th.mickmick.data.Nickname
import com.sweetguyfanclub2th.mickmick.data.Todo
import com.sweetguyfanclub2th.mickmick.data.UserInfo
import com.sweetguyfanclub2th.mickmick.databinding.ActivityRegisterBinding
import com.sweetguyfanclub2th.mickmick.ui.main.MainActivity
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var auth: FirebaseAuth? = null
    private lateinit var db: FirebaseFirestore
    private var moveMainStack : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.registercomplete.setOnClickListener {
            val email = binding.registerEmail.text.toString()
            val pw = binding.registerPasswd.text.toString()
            val repeatPw = binding.registerPasswd.text.toString()
            val nickname = binding.nickname.text.toString()

            textInvisibleSet()

            if (checkEmail(email)
                && checkPasswd(pw)
                && checkRepeatPasswd(pw, repeatPw)
                && checkNickName(nickname)
            ) {
                createUser(email, pw, nickname)
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

    private fun checkEmail(email : String): Boolean {
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

    private fun checkPasswd(passwd : String): Boolean {
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

    private fun checkRepeatPasswd(passwd : String, repeatPasswd : String): Boolean {
        val p = passwd == repeatPasswd

        when (p) {
            true -> return true
            false -> {
                binding.repeatPasswdCheckText.visibility = View.VISIBLE
                return false
            }
        }
    }

    private fun checkNickName(nickname : String): Boolean {
        val nicknameFormatCheck = "^[a-zA-Z0-9ㄱ-ㅎ가-힣]{2,10}\$"
        val p = Pattern.matches(nicknameFormatCheck, nickname)

        return when (p) {
            true -> true
            false -> {
                binding.nicknameCheckText.visibility = View.VISIBLE
                false
            }
        }

        // TODO : 닉네임 중복체크 (Firebase -> 닉네임 컬렉션 -> 네임즈 문서 -> arraylist 싹다 돌려서 확인)
    }

    @SuppressLint("SimpleDateFormat")
    private fun createUser(email : String, pw : String, nickname : String) {
        moveMainStack = 0

        auth?.createUserWithEmailAndPassword(
            email, pw
        )
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 01. 투두 데이터 셋
                    // 투두 데이터 셋 구성 - 순서대로 timestamp / title / place(address) / participant
                    val timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
                    val todoDataSet = listOf(
                            timestamp to listOf("미크미크에 가입했어요",
                                "$nickname 님의 핸드폰에서 생성되었습니다",
                                "미크미크, $nickname"),
                    )

                    // 이메일로 컬렉션을 구분
                    db.collection(email)
                        .document("todo") // 투두 문서 생성
                        .set(todoDataSet) // 투두 데이터 셋 생성
                        .addOnSuccessListener { documentReference ->
                            moveMainStack += 1
                            Log.d(ContentValues.TAG, "TODO 업로드에 성공하였습니다. ( stack : $moveMainStack )")
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "TODO 정보 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                            Log.d(ContentValues.TAG, "TODO 정보 업로드에 실패하였습니다.")
                        }


                    // 02. 유저정보 데이터셋
                    val userDataSet = UserInfo(
                        nickname,
                        null,
                        arrayListOf(timestamp),
                        "default",
                        null
                    )

                    db.collection(email)
                        .document("userinfo")
                        .set(userDataSet)
                        .addOnSuccessListener { documentReference ->
                            moveMainStack += 1
                            Log.d(ContentValues.TAG, "USERINFO 업로드에 성공하였습니다. ( stack : $moveMainStack )")
                        }
                        .addOnFailureListener { e ->
                            Log.d(ContentValues.TAG, "USERINFO 정보 업로드에 실패하였습니다.")
                        }


                    // 03. 닉네임 데이터셋
                    val nicknameDataSet = Nickname(arrayListOf(nickname))

                    db.collection("nickname")
                        .document("names")
                        .set(nicknameDataSet)
                        .addOnSuccessListener { documentReference ->
                            moveMainStack += 1
                            Log.d(ContentValues.TAG, "NICKNAME 업로드에 성공하였습니다. ( stack : $moveMainStack )")
                        }
                        .addOnFailureListener { e ->
                            Log.d(ContentValues.TAG, "NICKNAME 정보 업로드에 실패하였습니다.")
                        }

                } else if (task.exception?.message.isNullOrEmpty()) {
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

        when(moveMainStack){
            3 -> moveMainPage(auth?.currentUser)
            else -> Toast.makeText(this, "유저 정보 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun signIn(email : String, pw : String) {
        auth?.signInWithEmailAndPassword(email, pw)
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