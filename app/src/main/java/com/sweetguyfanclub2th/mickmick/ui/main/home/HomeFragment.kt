package com.sweetguyfanclub2th.mickmick.ui.main.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.ActivityRenameBinding
import com.sweetguyfanclub2th.mickmick.databinding.FragmentHomeBinding
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeFragment : Fragment() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var email = FirebaseAuth.getInstance().currentUser?.email.toString()
    private var todoList: MutableList<String>  = mutableListOf()
    private var recyclerItems: MutableList<List<String>>  = mutableListOf()

    @SuppressLint("NewApi", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)


        val current = LocalDateTime.now()
        val time = current.format((DateTimeFormatter.ofPattern("yyyyMMdd")))
        val showingTime = current.format(DateTimeFormatter.ofPattern("MM월 dd일"))

        findTodoId()

        binding.nowDate.text = "$showingTime, "

        // TODO: 유저 todo 목록에서 앞 연도, 월, 일이 현재 연도, 월, 일과 같은 것만 뽑아서 보여주기

        return binding.root
    }

    private fun findTodoId() {
        val info = db.collection(email).document("userinfo")

        info.get().addOnSuccessListener {
            val nickname: List<String> = it.get("todoId") as List<String>
            for (i in nickname.indices) {
                todoList.add(nickname[i])
            }
            Log.d("time1", nickname.toString())
            findRecyclerItem()
        }
    }

    private fun findRecyclerItem() {
        val todo = db.collection(email).document("todo")

        todo.get().addOnSuccessListener {
            for (i in 0 until todoList.size) {
                val todoTitle: List<String> = it.get(todoList[i]) as List<String>
                recyclerItems.add(todoTitle)
            }
            Log.d("time2", recyclerItems.toString())
        }

    }
}