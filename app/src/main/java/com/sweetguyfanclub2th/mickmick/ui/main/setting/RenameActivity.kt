package com.sweetguyfanclub2th.mickmick.ui.main.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.databinding.ActivityRenameBinding

class RenameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRenameBinding
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var itemToString: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRenameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        itemToString = listOf()

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
            checkNickName(binding.registerNickname.text.toString())
        }

        binding.backpress.setOnClickListener {
            finish()
        }

        val emailFind = FirebaseAuth.getInstance()
        val firebaseUser = emailFind.currentUser

        binding.registerEmail.isEnabled = false

        if (firebaseUser != null) {
            val email = firebaseUser.email.toString()
            binding.registerEmail.hint = email
            val userInfo = db.collection(email).document("userinfo")

            userInfo.get().addOnSuccessListener {
                val nickname = it.get("nickname").toString()
                val name = it.get("name").toString()
                binding.registerName.text =
                    Editable.Factory.getInstance().newEditable(name)
                binding.registerNickname.text =
                    Editable.Factory.getInstance().newEditable(nickname)
            }
        }
        else {
            binding.registerEmail.hint = "유저 정보가 잘못되었습니다."
        }

        binding.editComplete.setOnClickListener {
            if (binding.registerName.text.toString() == "") {
                editInfo(
                    "", binding.registerNickname.text.toString()
                )
            }
            else if (binding.registerNickname.text.toString() == "") {
                Toast.makeText(this, "닉네임은 반드시 입력해야 합니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                editInfo(
                    binding.registerName.text.toString(),
                    binding.registerNickname.text.toString()
                )
            }
        }
    }

    private fun editInfo(name: String, nickName: String) {
        try {
            val email = FirebaseAuth.getInstance().currentUser?.email.toString()
            val editUser = db.collection(email).document("userinfo")
            val beForeNickname = db.collection("nickname").document("names")

            beForeNickname.update(email, nickName)
            editUser.update("nickname", nickName)
            editUser.update("name", name)

            Toast.makeText(this, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        catch (nullPointerException: NullPointerException) {
            Toast.makeText(this, "유저 정보 불러오기에 실패했습니다.", Toast.LENGTH_SHORT).show()
            Log.d("TAG", "editInfo: NullPointerException")
            finish()
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
}