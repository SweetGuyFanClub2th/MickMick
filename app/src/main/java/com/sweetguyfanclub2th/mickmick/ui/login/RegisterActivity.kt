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
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.data.Nickname
import com.sweetguyfanclub2th.mickmick.data.Todo
import com.sweetguyfanclub2th.mickmick.data.UserInfo
import com.sweetguyfanclub2th.mickmick.databinding.ActivityRegisterBinding
import com.sweetguyfanclub2th.mickmick.ui.main.MainActivity
import com.sweetguyfanclub2th.mickmick.ui.splash.intro.WelcomeActivity.Companion.TAG
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.concurrent.thread

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var auth: FirebaseAuth? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var itemToString: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
//
//        db.collection("nickname").get()
//            .addOnSuccessListener { result ->
//                for (document in result) {
//                    itemToString = document["nickname"].toString()
//                        .replace("[", "")
//                        .replace("]", "")
//                        .split(",")
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.w("RegisterActivity", "Error getting documents: $exception")
//            }

        binding.nicknameCheck.setOnClickListener {
            checkNickName(binding.nickname.text.toString())
        }

        binding.registercomplete.setOnClickListener {
            textInvisibleSet()
            val email = binding.registerEmail.text.toString()
            val pw = binding.registerPasswd.text.toString()
            val repeatPw = binding.registerRepeatPasswd.text.toString()
            val nickname = binding.nickname.text.toString()

            if (checkEmail(email)
                && checkPasswd(pw)
                && checkRepeatPasswd(pw, repeatPw)
//                && !checkNickName(nickname)
            ) {
                createUser(email, pw, nickname)
            } else {
                Toast.makeText(this, "각 형식을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun textInvisibleSet() {
        binding.emailCheckText.visibility = View.INVISIBLE
        binding.passwdCheckText.visibility = View.INVISIBLE
        binding.repeatPasswdCheckText.visibility = View.INVISIBLE
    }

    private fun checkEmail(email: String): Boolean {
        val emailFormatCheck =
            "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        return when (Pattern.matches(emailFormatCheck, email)) {
            true -> true
            false -> {
                binding.emailCheckText.visibility = View.VISIBLE
                false
            }
        }
    }

    private fun checkPasswd(passwd: String): Boolean {
        val passwdFormatCheck =
            "^(?=.*[A-Za-z])(?=.*[0-9])(?=.*[\$@\$!%*#?&])[A-Za-z[0-9]\$@\$!%*#?&]{8,20}\$"

        return when (Pattern.matches(passwdFormatCheck, passwd)) {
            true -> true
            false -> {
                binding.passwdCheckText.visibility = View.VISIBLE
                false
            }
        }
    }

    private fun checkRepeatPasswd(passwd: String, repeatPasswd: String): Boolean {

        return when (passwd == repeatPasswd) {
            true -> true
            false -> {
                binding.repeatPasswdCheckText.visibility = View.VISIBLE
                false
            }
        }
    }

    private fun checkNickName(nickname : String): Boolean {
        val nicknameFormatCheck = "^[a-zA-Z0-9ㄱ-ㅎ가-힣]{2,20}\$"
        var isNicknameExist: Boolean = false

        for (element in itemToString) {
            if (element == nickname) {
                isNicknameExist = true
            }
        }

        return when (Pattern.matches(nicknameFormatCheck, nickname) && isNicknameExist) {
            true -> {
                binding.nicknameCheckText.text = "이미 사용중인 닉네임입니다."
                binding.nicknameCheckText.visibility = View.VISIBLE
                true
            }
            false -> {
                binding.nicknameCheckText.text = "사용할 수 있는 닉네임입니다."
                binding.nicknameCheckText.visibility = View.VISIBLE
                false
            }
        }
    }

    private fun createUser(email: String, pw: String, nickname: String) {
        auth?.createUserWithEmailAndPassword(
            email, pw
        )
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val timestamp = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
                    // 01. 투두 데이터 셋
                    // 투두 데이터 셋 구성 - 순서대로 timestamp : title / place(address) / participant
                    todoDataSetUpload(timestamp, nickname, email)

                    // 02. 유저정보 데이터셋
                    userInfoDataSetUpload(nickname, timestamp, email)

                    // 03. 닉네임 데이터셋
                    nicknameDataSetUpload(nickname, email)

                    moveMainPage(auth?.currentUser)
                } else {
                    Toast.makeText(
                        this,
                        "오류가 발생했습니다 ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun todoDataSetUpload(timestamp: String, nickname: String, email: String){
        val todoDataSet = Todo(
            mutableMapOf(timestamp to
            listOf(
                "미크미크에 가입했어요",
                "$nickname 님의 핸드폰에서 생성되었습니다",
                "미크, $nickname"))
        )

        // 이메일로 컬렉션을 구분
        db.collection(email)
            .document("todo") // 투두 문서 생성
            .set(todoDataSet) // 투두 데이터 셋 생성
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "TODO 업로드에 성공하였습니다.")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "TODO 정보 업로드에 실패하였습니다.")
            }
    }

    private fun userInfoDataSetUpload(nickname: String, timestamp: String, email: String) {
        val userDataSet = UserInfo(
            nickname,
            null,
            null,
            arrayListOf(timestamp),
            "default",
            null
        )

        db.collection(email)
            .document("userinfo")
            .set(userDataSet)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "USERINFO 업로드에 성공하였습니다.")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "USERINFO 정보 업로드에 실패하였습니다.")
            }
    }

    private fun nicknameDataSetUpload(nickname: String, email : String){
        val nicknameRef = db.collection("nickname").document("names")

        nicknameRef
            .update("nickname", FieldValue.arrayUnion(mutableMapOf(nickname to listOf(email))))
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "NICKNAME 업로드에 성공하였습니다.")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "NICKNAME 정보 업로드에 실패하였습니다.")
            }
    }

    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(this, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}