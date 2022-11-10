package com.sweetguyfanclub2th.mickmick.ui.main.todo.menu

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.FragmentScheduleBinding
import java.util.*


class ScheduleFragment : Fragment() {
    private var _binding: FragmentScheduleBinding? = null
    private val binding get() = _binding!!

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()  // Firestore 인스턴스 선언
    private var email = FirebaseAuth.getInstance().currentUser?.email.toString()
    private lateinit var nickname: String

    private lateinit var dateValue: String
    private lateinit var timeValue: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScheduleBinding.inflate(inflater, container, false)

        binding.editDate.setOnClickListener {
            Log.d("ddede", "openDateDialog")
            openDateDialog()
        }

        binding.editTime.setOnClickListener {
            Log.d("ddede", "openDateDialog")
            openTimeDialog()
        }


        return binding.root
    }

    private fun openDateDialog() {
        val today = GregorianCalendar()

        val year: Int = today.get(Calendar.YEAR)
        val month: Int = today.get(Calendar.MONTH)
        val date: Int = today.get(Calendar.DATE)

        // show dialog
        val dialog = DatePickerDialog(requireContext(), { _, year, month, date ->
            binding.editDate.text =
                Editable.Factory.getInstance().newEditable("${year}년 ${month+1}월 ${date}일")
            dateValue = "${year}${month+1}${date}"
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
        }, hour, minute, true)

        dialog.show()
    }
}
