package com.sweetguyfanclub2th.mickmick.ui.main.todo.menu

import android.content.ContentValues
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.FragmentScheduleBinding


class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.addTodoBtn.setOnClickListener() {
            var todo = binding.addTodoEdit.text.toString();  // 버튼 클릭시 입력한 텍스트를 문자열로 todo에 저장
            var email = FirebaseAuth.getInstance().currentUser?.email.toString()
            todoDataSetUpload(todo, email);
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun todoDataSetUpload(todo: String, email : String){
        val nicknameRef = db.collection("nickname").document("names")

        nicknameRef
            .update("todo", FieldValue.arrayUnion(mutableMapOf(todo to listOf(email))))
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "todo 업로드에 성공하였습니다.")
            }
            .addOnFailureListener {
                Log.d(ContentValues.TAG, "todo 정보 업로드에 실패하였습니다.")
            }
    }
}