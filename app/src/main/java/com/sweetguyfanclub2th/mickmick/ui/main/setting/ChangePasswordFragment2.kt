package com.sweetguyfanclub2th.mickmick.ui.main.setting

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sweetguyfanclub2th.mickmick.databinding.FragmentChangePassword2Binding

class ChangePasswordFragment2 : Fragment() {
    private var _binding: FragmentChangePassword2Binding? = null
    private val binding get() = _binding!!
    private val email = FirebaseAuth.getInstance().currentUser?.email.toString()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePassword2Binding.inflate(inflater, container, false)
        binding.passwordFirstCheck.text =
            Editable.Factory.getInstance().newEditable(email)
        binding.editComplete.setOnClickListener {
            send(binding.passwordFirstCheck.text.toString())
        }

        return binding.root
    }

    private fun send(em: String) {
        val auth = Firebase.auth

        auth.sendPasswordResetEmail(em)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(activity, "이메일을 보냈습니다. 다시 로그인해주세요.", Toast.LENGTH_SHORT).show()
                    auth.signOut()
                    (activity as ChangePasswordActivity).gotoSplash()
                }
            }
    }
}