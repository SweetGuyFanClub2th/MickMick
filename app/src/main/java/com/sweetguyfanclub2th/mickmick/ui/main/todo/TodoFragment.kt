package com.sweetguyfanclub2th.mickmick.ui.main.todo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.FragmentTodoBinding
import com.sweetguyfanclub2th.mickmick.ui.main.MainActivity
import com.sweetguyfanclub2th.mickmick.ui.main.friend.FriendListActivity
import com.sweetguyfanclub2th.mickmick.ui.main.home.HomeFragment
import com.sweetguyfanclub2th.mickmick.ui.main.search.SearchPlaceFragment
import java.util.*


class TodoFragment : Fragment() {
    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()  // Firestore 인스턴스 선언
    private var email = FirebaseAuth.getInstance().currentUser?.email.toString()

    private lateinit var dateValue: String
    private lateinit var timeValue: String

    private lateinit var selectTime: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)

        val num1 = arguments?.getString("message")

        val editUser = db.collection(email).document("userinfo")
            .get().addOnSuccessListener {
                val cacheFriend = it.get("cacheFriend")
                Log.d("cacheFriend", cacheFriend.toString())

                if (cacheFriend != null) {
                    binding.editFriend.setText(cacheFriend.toString())
                    binding.editFriend.isEnabled = false
                }
            }


        Log.d("투두 프래그먼트", num1.toString())

        if (num1 != null) {
            val mainActivity = activity as MainActivity
            timeValue = mainActivity.savingTextShow(
                binding.todoName,
                binding.editDate,
                binding.editTime,
                binding.editFriend,
                binding.editPlace
            ).toString()
            binding.editPlace.setText(num1)
            binding.editPlace.isEnabled = false
        }

        val number = arguments?.getString("fuck")
        Log.d("친구 프래그먼트", number.toString())
        binding.editFriend.setText(number)

        //val num2 = arguments?.getString("num3")

        /*if (num2 != null) {
            binding.editFriend.setText(num2)
            binding.editFriend.isEnabled = false
        }*/
        //val num = arguments?.getString("num")
        //if (num != null) {
         //   binding.editFriend.setText(num)
         //   binding.editFriend.isEnabled = false
        //}
        //Log.d("친구이름", num.toString())

        binding.editDate.setOnClickListener {
            openDateDialog()
        }

        binding.editTime.setOnClickListener {
            openTimeDialog()
        }

        binding.editFriend.setOnClickListener {
            activity?.let {
                val intent = Intent(context, FriendListActivity::class.java)
                startActivity(intent)
            }
            //  val friendName = arguments?.getInt("친구이름").toString()
            //Log.e("확인",friendName)

            // TODO
        }

        //val intent = Intent(activity, FriendListActivity::class.java)

        //val value1 = intent.getStringExtra("친구이름")
        //Log.e("확인",value1.toString())

        val editUser1 = db.collection(email).document("userinfo")



        binding.editPlace.setOnClickListener {
            val mainActivity = activity as MainActivity
            mainActivity.editTextSaving(
                binding.todoName,
                binding.editDate,
                binding.editTime,
                binding.editFriend,
                binding.editPlace,
                selectTime
            )
            mainActivity.changeToSearchFragment()
        }
        val myEmail = Firebase.auth.currentUser?.email.toString()

        binding.addTodoBtn.setOnClickListener {
            when (scheduleNullCheck(
                binding.todoName,
                binding.editDate,
                binding.editTime,
                binding.editFriend,
                binding.editPlace
            )) {
                true -> {uploadData();editUser1.update("cacheFriend",null);editUser1.update("todoFriend",
                    arrayListOf(myEmail))}
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

    private fun openDateDialog() {
        val today = GregorianCalendar()

        val year: Int = today.get(Calendar.YEAR)
        val month: Int = today.get(Calendar.MONTH)
        val date: Int = today.get(Calendar.DATE)

        // show dialog
        val dialog = DatePickerDialog(requireContext(), { _, year, month, date ->
            binding.editDate.text =
                Editable.Factory.getInstance().newEditable("${year}${month + 1}${date}")
        }, year, month, date)

        dialog.show()
    }

    private fun openTimeDialog() {
        val today = GregorianCalendar()

        val hour: Int = today.get(Calendar.HOUR)
        val minute: Int = today.get(Calendar.MINUTE)

        // show dialog
        val dialog = TimePickerDialog(requireContext(), { _, hour, minute ->
            if (minute < 10) {
                binding.editTime.text =
                    Editable.Factory.getInstance().newEditable("${hour}:0${minute}")
                selectTime = "${hour}0${minute}"
            } else {
                binding.editTime.text =
                    Editable.Factory.getInstance().newEditable("${hour}:${minute}")
                selectTime = "${hour}${minute}"
            }
        }, hour, minute, true)

        dialog.show()
    }

    private fun uploadData() {
        val tododata1 = db.collection(email).document("userinfo")

        tododata1.get().addOnSuccessListener {
            val requestedEmail: List<String> = it.get("todoFriend") as List<String>
            var count = requestedEmail.size

            while (count != 0) {

                val info = db.collection(requestedEmail[count-1]).document("todo")
                val tododata = db.collection(requestedEmail[count-1]).document("userinfo")
                val poi = arguments?.getString("poi")
                --count

                info.update(
                    (binding.editDate.text.toString() + timeValue),
                    FieldValue.arrayUnion(
                        binding.todoName.text.toString(),
                        binding.editDate.text.toString(),
                        binding.editTime.text.toString(),
                        binding.editFriend.text.toString(),
                        binding.editPlace.text.toString(),
                        poi.toString()
                    )
                )

                val emptyList = ArrayList<String>()

                tododata.update(
                    "todoId", FieldValue.arrayUnion(
                        binding.editDate.text.toString() + timeValue
                    )
                )
                tododata.get().addOnSuccessListener {
                    val nickname: List<String> = it.get("todoId") as List<String>
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

            }
        }


    }
}
