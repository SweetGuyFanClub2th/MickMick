package com.sweetguyfanclub2th.mickmick.ui.login

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.data.FriendSearch
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
import kotlin.properties.Delegates

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private var auth: FirebaseAuth? = null
    private lateinit var db: FirebaseFirestore
    private lateinit var itemToString: List<String>
    private var nickNameCheck: Boolean = false
    private lateinit var checkNickname: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        nickNameCheck = false

        db.collection("nickname").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    itemToString = document["nickname"].toString()
                        .replace("[", "")
                        .replace("]", "")
                        .split(",")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("RegisterActivity", "Error getting documents: $exception")
            }

        binding.nicknameCheck.setOnClickListener {

            checkNickname = binding.nickname.text.toString()
            if (nickNameCheckFormat(checkNickname)) {
                if (checkNickName(checkNickname)) {
                    nickNameCheck = true
                }
            }
        }

        binding.registercomplete.setOnClickListener {
            textInvisibleSet()
            val email = binding.registerEmail.text.toString()
            val pw = binding.registerPasswd.text.toString()
            val repeatPw = binding.registerRepeatPasswd.text.toString()
            val nickname = binding.nickname.text.toString()
            val name = binding.name.text.toString()

            if (registerNullableCheck(
                    binding.registerEmail, binding.registerPasswd,
                    binding.registerRepeatPasswd, binding.nickname, binding.name
                )
            ) {
                if (nickNameCheck && (checkNickname == nickname)) {
                    if (checkEmail(email)
                        && checkPasswd(pw)
                        && checkRepeatPasswd(pw, repeatPw)
                        && nickNameCheckFormat(nickname)
                    ) {
                        createUser(email, pw, nickname, name)
                    } else {
                        Toast.makeText(this, "각 형식을 확인해주세요", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "닉네임 형식체크가 되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "입력되지 않은 칸이 존재합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun textInvisibleSet() {
        binding.emailCheckText.visibility = View.INVISIBLE
        binding.passwdCheckText.visibility = View.INVISIBLE
        binding.repeatPasswdCheckText.visibility = View.INVISIBLE
        binding.nicknameCheckText.visibility = View.INVISIBLE
    }

    private fun registerNullableCheck(
        email: EditText?, passwd: EditText?,
        repeatPasswd: EditText?, nickname: EditText?, name : EditText?
    ): Boolean {
        return !(email?.text.isNullOrEmpty() || passwd?.text.isNullOrEmpty()
                || repeatPasswd?.text.isNullOrEmpty() || nickname?.text.isNullOrEmpty()
                || name?.text.isNullOrEmpty())
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

    @SuppressLint("SetTextI18n")
    private fun checkRepeatPasswd(passwd: String, repeatPasswd: String): Boolean {
        binding.nicknameCheckText.visibility = View.INVISIBLE
        return when (passwd == repeatPasswd) {
            true -> true
            false -> {
                binding.nicknameCheckText.text = "특수문자, 영문, 숫자를 조합한 8~10글자로 설정하세요"
                binding.repeatPasswdCheckText.visibility = View.VISIBLE
                false
            }
        }
    }

    private fun checkNickName(nickname: String): Boolean {
        var sameNickNameCheck = false

        for (element in itemToString) {
            if (element != nickname) {
                sameNickNameCheck = true
            } else {
                sameNickNameCheck = false
                break
            }
        }

        return when (sameNickNameCheck) {
            false -> {
                binding.nicknameCheckText.visibility = View.VISIBLE
                binding.nicknameCheckText.text = "이미 사용중인 닉네임입니다."
                false
            }
            true -> {
                binding.nicknameCheckText.visibility = View.VISIBLE
                binding.nicknameCheckText.text = "사용할 수 있는 닉네임입니다."
                true
            }
        }
    }

    private fun nickNameCheckFormat(nickname: String): Boolean {
        val nicknameFormatCheck = "^[a-zA-Z0-9ㄱ-ㅎ가-힣]{2,20}\$"

        return when (Pattern.matches(nicknameFormatCheck, nickname)) {
            true -> true
            false -> {
                binding.nicknameCheckText.visibility = View.VISIBLE
                false
            }
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun createUser(email: String, pw: String, nickname: String, name : String) {
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
                    userInfoDataSetUpload(nickname, name, timestamp, email)

                    // 03. 닉네임 데이터셋
                    nicknameDataSetUpload(nickname)

                    // 04. 친구 검색 데이터셋
                    friendSearchDataSetUpload(nickname, email)

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

    private fun todoDataSetUpload(timestamp: String, nickname: String, email: String) {
        val todoDataSet =
            mutableMapOf(
                "209912312359" to
                        listOf(
                            "미크미크와 함께하기",
                            "2099년 12월 31일",
                            "23시 59분",
                            "미크미크 개발자들",
                            "당신이 있는 모든 곳"
                        )
            )

        // 이메일로 컬렉션을 구분
        val emails = auth?.currentUser?.email.toString()
        db.collection(emails).document("todo").set(
            todoDataSet
        )
        .addOnSuccessListener {
            Log.d("TodoDataSet", "TodoDataSetUpload Success")
        }
        .addOnFailureListener {
            Log.d("TodoDataSet", "TodoDataSetUpload Fail")
        }
    }

    private fun userInfoDataSetUpload(
        nickname: String,
        name: String,
        timestamp: String,
        email: String
    ) {
        val userDataSet = UserInfo(
            nickname,
            name,
            null,
            null,
            arrayListOf("209912312359"),
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

    private fun nicknameDataSetUpload(nickname: String) {
        val nicknameRef = db.collection("nickname").document("names")

        nicknameRef
            .update("nickname", FieldValue.arrayUnion(nickname))
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "NICKNAME 업로드에 성공하였습니다.")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "NICKNAME 정보 업로드에 실패하였습니다.")
            }
    }

    private fun friendSearchDataSetUpload(nickname: String, email: String) {
        val friendRef = db.collection("friendSearch")
        val friendSearchDataSet = FriendSearch(
            nickname, email
        )

        friendRef
            .add(friendSearchDataSet)
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "FRIENDSEARCH 업로드에 성공하였습니다.")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "FRIENDSEARCH 정보 업로드에 실패하였습니다.")
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