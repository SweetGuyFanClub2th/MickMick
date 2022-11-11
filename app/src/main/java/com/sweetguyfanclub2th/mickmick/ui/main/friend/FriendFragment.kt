package com.sweetguyfanclub2th.mickmick.ui.main.friend

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.FragmentFriendBinding
import com.sweetguyfanclub2th.mickmick.ui.main.setting.RenameActivity


class FriendFragment : Fragment() {
    private var _binding: FragmentFriendBinding? = null
    private val binding get() = _binding!!

    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFriendBinding.inflate(inflater, container, false)


        binding.apply{
            screenChange.setOnClickListener{
                val intent = Intent(activity, FriendSearchActivity::class.java)
                startActivity(intent)
            }
            gotoFriendRequest.setOnClickListener{
                val intent = Intent(activity, FriendRequestActivity::class.java)
                startActivity(intent)
            }
        }

        val view = binding.root
        return view






    }


}