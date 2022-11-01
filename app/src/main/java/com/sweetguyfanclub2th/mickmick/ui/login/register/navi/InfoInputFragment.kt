package com.sweetguyfanclub2th.mickmick.ui.login.register.navi

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.ActivityLoginBinding.inflate
import com.sweetguyfanclub2th.mickmick.databinding.FragmentInfoInputBinding
import com.sweetguyfanclub2th.mickmick.ui.main.MainActivity
import java.util.regex.Pattern

class InfoInputFragment : Fragment() {
    private lateinit var binding : FragmentInfoInputBinding
    private var auth: FirebaseAuth? = null
    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = InfoInputFragment.inflate(inflater, container, false)
        val view = binding.root

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        binding.registercomplete.setOnClickListener {
            binding.emailCheckText.visibility = View.INVISIBLE
            binding.passwdCheckText.visibility = View.INVISIBLE
            binding.repeatPasswdCheckText.visibility = View.INVISIBLE

            val checkEmailResult = checkEmail()
            val checkPasswdResult = checkPasswd()
            val checkRepeatPasswdResult = checkRepeatPasswd()

            if (checkEmailResult && checkPasswdResult && checkRepeatPasswdResult) {
                createUser()
            } else {
                Toast.makeText(activity, "각 형식을 확인해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        return view
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

        activity?.let {
            auth?.createUserWithEmailAndPassword(
                email, pw
            )
                ?.addOnCompleteListener(it) { task ->
                    if (task.isSuccessful) {
                        val user = hashMapOf(
                            "user" to email,
                            "testMaxScore" to 0,
                            "testRecentScore" to 0
                        )

                        db.collection("users")
                            .document(email)
                            .set(user)
                            .addOnSuccessListener { documentReference ->
                                Log.d(ContentValues.TAG, "유저 정보 업로드 및 회원가입에 성공하였습니다.")
                                moveMainPage(task.result?.user)
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(activity, "유저 정보 업로드에 실패했습니다.", Toast.LENGTH_SHORT).show()
                            }
                    } else if (task.exception?.message.isNullOrEmpty()) {
                        Toast.makeText(activity, task.exception?.message, Toast.LENGTH_SHORT).show()
                    } else {
                        signIn()
                    }
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
                    Log.d(ContentValues.TAG, "signInWithEmail:success")
                    moveMainPage(task.result?.user)
                } else {
                    Log.d(ContentValues.TAG, "failed")
                    Toast.makeText(activity, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun moveMainPage(user: FirebaseUser?) {
        if (user != null) {
            Toast.makeText(activity, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()
            startActivity(Intent(activity, MainActivity::class.java))
        }
    }


}