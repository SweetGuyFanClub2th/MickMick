package com.sweetguyfanclub2th.mickmick.ui.main.setting

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R

import com.sweetguyfanclub2th.mickmick.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

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

            var imgData: Int = 0

            when (iconType) {
                "red" -> imgData = R.drawable.c_red
                "yellow" -> imgData = R.drawable.c_yellow
                "default" -> imgData = R.drawable.c_green
                "blue" -> imgData = R.drawable.c_blue
                "purple" -> imgData = R.drawable.c_purple
                "gray" ->  imgData = R.drawable.c_gray
            }

            binding.mainProfileImage2.setImageResource(imgData)
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
}