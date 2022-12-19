package com.sweetguyfanclub2th.mickmick.ui.main.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.FragmentScheduleBinding
import com.sweetguyfanclub2th.mickmick.ui.main.home.HomeFragment
import java.util.*
import kotlin.collections.ArrayList


class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()  // Firestore 인스턴스 선언
    private var email = FirebaseAuth.getInstance().currentUser?.email.toString()

    private lateinit var dateValue: String
    private lateinit var timeValue: String

    private lateinit var selectDate: String
    private lateinit var selectHour: String
    private lateinit var selectMinute: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.editDate.setOnClickListener {
            openDateDialog()
        }
        binding.editTime.setOnClickListener {
            openTimeDialog()
        }
        binding.editFriend.setOnClickListener {
            // TODO
        }
        binding.editPlace.setOnClickListener {
            // TODO
        }
        binding.addTodoBtn.setOnClickListener {
            when (scheduleNullCheck(
                binding.todoName,
                binding.editDate,
                binding.editTime,
                binding.editFriend,
                binding.editPlace
            )) {
                true -> uploadData()
                else -> Toast.makeText(activity, "입력하지 않은 칸이 존재합니다", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun scheduleNullCheck(
        todoName: EditText?, editDate: EditText?,
        editTime: EditText?, editFriend: EditText?, editPlace: EditText?
    ): Boolean {
        return !(todoName?.text.isNullOrEmpty() || editDate?.text.isNullOrEmpty()
                || editTime?.text.isNullOrEmpty() || editFriend?.text.isNullOrEmpty()
                || editPlace?.text.isNullOrEmpty())
    }

    private fun uploadData() {
        val info = db.collection(email).document("todo")
        val tododata = db.collection(email).document("userinfo")

        info.update(
            (dateValue + timeValue), FieldValue.arrayUnion(
                binding.todoName.text.toString(),
                selectDate,
                "$selectHour:$selectMinute",
                binding.editFriend.text.toString(),
                binding.editPlace.text.toString(),
            )
        )

        var emptyList = ArrayList<String>()

        tododata.update("todoId", FieldValue.arrayUnion(
            dateValue + timeValue
        ))
        tododata.get().addOnSuccessListener {
            var nickname: List<String> = it.get("todoId") as List<String>
            for (i in nickname.indices) {
                emptyList.add(nickname[i])
            }
            Log.d("apple1", emptyList.toString())
            emptyList.sort()
            Log.d("apple2", emptyList.toString())
            tododata.update("todoId", emptyList)
        }

        val transaction: FragmentTransaction =
            requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment, HomeFragment())
        transaction.commit()

        // TODO
    }

    private fun openDateDialog() {
        val today = GregorianCalendar()

        val year: Int = today.get(Calendar.YEAR)
        val month: Int = today.get(Calendar.MONTH)
        val date: Int = today.get(Calendar.DATE)

        // show dialog
        val dialog = DatePickerDialog(requireContext(), { _, year, month, date ->
            binding.editDate.text =
                Editable.Factory.getInstance().newEditable("${year}년 ${month + 1}월 ${date}일")
            dateValue = "${year}${month + 1}${date}"
            selectDate = year.toString() + (month + 1).toString() + date.toString()
        }, year, month, date)

        dialog.show()
    }

    private fun openTimeDialog() {
        val today = GregorianCalendar()

        val hour: Int = today.get(Calendar.HOUR)
        val minute: Int = today.get(Calendar.MINUTE)

        // show dialog
        val dialog = TimePickerDialog(requireContext(), { _, hour, minute ->
            binding.editTime.text =
                Editable.Factory.getInstance().newEditable("${hour}시 ${minute}분")
            timeValue = "${hour}${minute}"
            selectHour = hour.toString()
            selectMinute = minute.toString()
        }, hour, minute, true)

        dialog.show()
    }
}
