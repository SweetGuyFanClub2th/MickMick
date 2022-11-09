package com.sweetguyfanclub2th.mickmick.ui.main.setting

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.sweetguyfanclub2th.mickmick.databinding.FragmentChangePassword1Binding

class ChangePasswordFragment1 : Fragment() {
    private var _binding: FragmentChangePassword1Binding? = null
    private val binding get() = _binding!!
    var auth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChangePassword1Binding.inflate(inflater, container, false)
        auth = FirebaseAuth.getInstance()

        val emailFind = FirebaseAuth.getInstance()
        val firebaseUser = emailFind.currentUser
        val email = firebaseUser?.email.toString()

        binding.apply {
            editComplete.setOnClickListener {
                if (passwordFirstCheck.text.toString() != null) {
                    val passwd = binding.passwordFirstCheck.text.toString()
                    signIn(email, passwd)
                }
            }
        }

        return binding.root
    }

    private fun signIn(email : String, passwd : String) {
        auth?.signInWithEmailAndPassword(email, passwd)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    (activity as ChangePasswordActivity).gotoFragment2()
                }
                else {
                    Toast.makeText(activity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}