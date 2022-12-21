package com.sweetguyfanclub2th.mickmick.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.data.TodoData
import com.sweetguyfanclub2th.mickmick.databinding.FragmentHomeBinding
import com.sweetguyfanclub2th.mickmick.ui.main.home.detail.HomeDetailActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Suppress("UNCHECKED_CAST")
class HomeFragment : Fragment() {
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var email = FirebaseAuth.getInstance().currentUser?.email.toString()
    private var todoList: MutableList<String> = mutableListOf()
    private var recyclerItems: MutableList<List<String>> = mutableListOf()
    private var idx : Int = 0

    private lateinit var todayTime: String

    @SuppressLint("NewApi", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        val current = LocalDateTime.now()
        val time = current.format((DateTimeFormatter.ofPattern("yyyyMMdd")))
        val showingTime = current.format(DateTimeFormatter.ofPattern("MM월 dd일"))

        todayTime = time
        findTodoId()
        binding.nowDate.text = "$showingTime, "

        binding.emp.setOnClickListener {
            val intent = Intent(context, HomeDetailActivity::class.java)

            intent.putExtra("title", binding.todoTitle.text)
            intent.putExtra("time", binding.todoDate.text)
            intent.putExtra("member", binding.todoMember.text)
            intent.putExtra("place", binding.todoPlace.text)
            intent.putExtra("poi", recyclerItems[idx][5])

            context?.let { it1 -> ContextCompat.startActivity(it1, intent, null) }
        }

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

    fun checkToday(date: String): Boolean {
        return date == todayTime
    }


    private fun findRecyclerItem() {
        val todo = db.collection(email).document("todo")

        todo.get().addOnSuccessListener {
            for (i in 0 until todoList.size) {
                val todoTitle: List<String> = it.get(todoList[i]) as List<String>
                recyclerItems.add(todoTitle)
            }
            Log.d("time2", recyclerItems.toString())

            val isFirst = true
            val recyclerViewItems = ArrayList<TodoData>()
            for (i in 1 until recyclerItems.size) {
                if (checkToday(recyclerItems[i][1])) {
                    if (isFirst) {
                        binding.emp.visibility = View.VISIBLE
                        binding.todoTitle.text = recyclerItems[i][2]
                        binding.todoDate.text = recyclerItems[i][0]
                        binding.todoMember.text = recyclerItems[i][3]
                        binding.todoPlace.text = recyclerItems[i][4]
                        idx = i
                    } else {
                        recyclerViewItems.add(
                            TodoData(
                                recyclerItems[i][0],
                                recyclerItems[i][1],
                                recyclerItems[i][2],
                                recyclerItems[i][3],
                                recyclerItems[i][4],
                                recyclerItems[i][5]
                            )
                        )
                    }
                    binding.todoRecycler.layoutManager = LinearLayoutManager(this.context)
                    binding.todoRecycler.adapter = HomeAdapter(recyclerViewItems)
                }
            }
        }
    }
}