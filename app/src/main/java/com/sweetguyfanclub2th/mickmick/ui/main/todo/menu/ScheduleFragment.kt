package com.sweetguyfanclub2th.mickmick.ui.main.todo.menu

import android.content.ContentValues
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.FragmentScheduleBinding


class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()  // Firestore 인스턴스 선언
    private var email = FirebaseAuth.getInstance().currentUser?.email.toString()
    private lateinit var nickname: String


//    private lateinit var database: DatabaseReference
//    database = Firebase.database.reference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.addTodoBtn.setOnClickListener() {
            var todo = binding.addTodoEdit.text.toString();  // 버튼 클릭시 입력한 텍스트를 문자열로 todo에 저장
            todoDataSetUpload(todo, email);

        }


        return binding.root
    }

    private fun updateData(){
        var todo = binding.addTodoEdit.text.toString();  // 버튼 클릭시 입력한 텍스트를 문자열로 todo에 저장

        var map= mutableMapOf<String,Any>()
        map["todo"] = todo.toString();

    }


    private fun todoDataSetUpload(todo: String, email : String){

        updateData()

        fun updateData(){
            val todo = binding.addTodoEdit.text.toString();  // 버튼 클릭시 입력한 텍스트를 문자열로 todo에 저장
            val email = FirebaseAuth.getInstance().currentUser?.email.toString()  // 사용자의 email을 firebase에서 불러와 저장함

            var map= mutableMapOf<String,Any>()
            map["todo"] = todo.toString();

            getEmail(email)

            db.collection(email).document("todo").update(map)  // email을 기준으로 컬렉션을 고르고, 투두 문서에 내용을 저장?
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        // 프래그먼트 새로고침
                    }
                }
        }

    }

    private fun getEmail(email: String) {
        val todoRef = db.collection(email).document("names")
        todoRef.get().addOnSuccessListener {
            nickname = it.get("nickname").toString()
        }
    }
}
