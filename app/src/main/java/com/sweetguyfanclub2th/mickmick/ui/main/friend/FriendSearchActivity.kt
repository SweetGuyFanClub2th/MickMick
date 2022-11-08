package com.sweetguyfanclub2th.mickmick.ui.main.friend


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.ActivityFriendsearchBinding

class FriendSearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendsearchBinding
    private var firestore: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendsearchBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        binding.recyclerview.adapter = FriendSearchAdapter()
        binding.recyclerview.layoutManager = LinearLayoutManager(this)

        var searchOption = "nickname"

        // 스피너 옵션에 따른 동작
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (binding.spinner.getItemAtPosition(position)) {
                    "닉네임" -> {
                        searchOption = "nickname"
                    }
                    "이메일" -> {
                        searchOption = "email1"
                    }
                }
            }
        }
        binding.searchBtn.setOnClickListener {
            (binding.recyclerview.adapter as FriendSearchAdapter).search(
                binding.searchWord.text.toString(),
                searchOption
            )
        }
    }
}
