package com.sweetguyfanclub2th.mickmick.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
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
import com.sweetguyfanclub2th.mickmick.data.searchcongestion.CongestionResponse
import com.sweetguyfanclub2th.mickmick.databinding.FragmentHomeBinding
import com.sweetguyfanclub2th.mickmick.ui.main.home.detail.HomeDetailActivity
import com.sweetguyfanclub2th.mickmick.ui.SKRetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

            context?.let { it1 ->
                ContextCompat.startActivity(it1, intent, null)
            }
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

    private fun checkToday(date: String): Boolean {
        return date == todayTime
    }

    private fun findRecyclerItem() {
        val todo = db.collection(email).document("todo")

        todo.get().addOnSuccessListener {
            for (i in 0 until todoList.size) {
                val todoTitle: List<String> = it.get(todoList[i]) as List<String>
                recyclerItems.add(todoTitle)
                Log.d("time0", todoTitle.toString())
            }
            Log.d("time2", recyclerItems.toString() + recyclerItems.toString() + recyclerItems.size.toString())

            val recyclerViewItems = ArrayList<TodoData>()
            for (i in 0 until recyclerItems.size) {
                if (recyclerItems[i][1] == todayTime) {
                    var confusion: String = ""
                    Log.d("Happy", todoList.toString() + recyclerItems[i].toString())

                    val retrofit = Retrofit.Builder()
                        .baseUrl("https://apis.openapi.sk.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    val api = retrofit.create(SKRetrofitService::class.java)
                    val callConfusion = api.getCongestion(
                        "application/json",
                        "l7xx7de642979fac440f8fad597ef2584f9e",
                        recyclerItems[i][5].toString()
                    )

                    callConfusion.enqueue(object : Callback<CongestionResponse> {
                        @SuppressLint("SetTextI18n")
                        override fun onResponse(
                            call: Call<CongestionResponse>,
                            response: Response<CongestionResponse>
                        ) {
                            Log.d("todoP", "성공 : ${response.raw()}")
                            try {
                                Log.d("todoP", response.body().toString().split("code=")[1].split(",")[0])
                                val confusionScore = response.body().toString().split("congestionLevel=")[1].split(",")[0]
                                Log.d("todoP", "성공 : $confusionScore")

                                when(confusionScore.toInt()) {
                                    1, 2 -> {
                                        confusion = "여유로운 "
                                    }
                                    3, 4, 5, 6 -> {
                                        confusion = "보통인"
                                    }
                                    7, 8 -> {
                                        confusion = "혼잡한"
                                    }
                                    9, 10 -> {
                                        confusion = "매우 혼잡한"
                                    }
                                }
                            }
                            catch (e: Exception) {
                                confusion = "혼잡도 자료가 없습니다."
                            }
                            if (confusion != "자료 없음") {
                                confusion = "현재 $confusion 상황입니다."
                            }
                            else {
                                confusion = "혼잡도 자료가 없습니다."
                            }
                        }

                        override fun onFailure(call: Call<CongestionResponse>, t: Throwable) {
                            Log.d("todoP", "실패 : $t")
                        }
                    })

                    if (i == 0) {
                        binding.emp.visibility = View.VISIBLE
                        binding.todoTitle.text = recyclerItems[i][2]
                        binding.todoDate.text = recyclerItems[i][0]
                        binding.todoMember.text = recyclerItems[i][3]
                        binding.todoPlace.text = recyclerItems[i][4]
                        binding.todoConfusion.text = confusion
                        idx = i
                    }

                    else {
                        Log.d("adede", recyclerViewItems.toString())
                        recyclerViewItems.add(
                            TodoData(
                                recyclerItems[i][0],
                                recyclerItems[i][1],
                                recyclerItems[i][2],
                                recyclerItems[i][3],
                                recyclerItems[i][4],
                                recyclerItems[i][5],
                            )
                        )
                        idx = i
                    }
                }
                binding.todoRecycler.layoutManager = LinearLayoutManager(this.context)
                binding.todoRecycler.adapter = HomeAdapter(recyclerViewItems)
            }
        }
    }
}