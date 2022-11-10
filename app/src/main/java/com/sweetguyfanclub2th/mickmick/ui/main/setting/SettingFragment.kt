package com.sweetguyfanclub2th.mickmick.ui.main.setting

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R

import com.sweetguyfanclub2th.mickmick.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    private var imgData: Int = 0
    private var backColor: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)

        val email = FirebaseAuth.getInstance().currentUser?.email.toString()
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val userInfo = db.collection(email).document("userinfo")

        userInfo.get().addOnSuccessListener {
            val nickname = it.get("nickname").toString()
            val name = it.get("name").toString()
            val iconType = it.get("iconType").toString()

            binding.mainProfileName.text = nickname
            binding.mainProfileName2.text = name

            returnColor(iconType)
        }


        binding.apply {
            list1.setOnClickListener {
                val intent = Intent(activity, RenameActivity::class.java)
                startActivity(intent)
            }
            list2.setOnClickListener {
                val intent = Intent(activity, ChangeIconActivity::class.java)
                startActivity(intent)
            }
            list3.setOnClickListener {
                val intent = Intent(activity, ChangePasswordActivity::class.java)
                startActivity(intent)
            }
        }

        return binding.root
    }

    fun returnColor(iconType: String) {
        when (iconType) {
            "red" -> {
                imgData = R.drawable.c_red
                backColor = R.drawable.btn_red
            }
            "yellow" -> {
                imgData = R.drawable.c_yellow
                backColor = R.drawable.btn_yellow
            }
            "default" -> {
                imgData = R.drawable.c_green
                backColor = R.drawable.btn_signature
            }
            "blue" -> {
                imgData = R.drawable.c_blue
                backColor = R.drawable.btn_blue
            }
            "purple" -> {
                imgData = R.drawable.c_purple
                backColor = R.drawable.btn_purple
            }
            "gray" -> {
                imgData = R.drawable.c_gray
                backColor = R.drawable.btn_gray
            }
        }

        binding.mainProfileImage2.setImageResource(imgData)
        binding.mainProfileImage.setBackgroundResource(backColor)
    }
}