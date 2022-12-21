package com.sweetguyfanclub2th.mickmick.ui.main

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.databinding.ActivityMainBinding
import com.sweetguyfanclub2th.mickmick.ui.main.friend.FriendFragment
import com.sweetguyfanclub2th.mickmick.ui.main.home.HomeFragment
import com.sweetguyfanclub2th.mickmick.ui.main.search.SearchPlaceFragment
import com.sweetguyfanclub2th.mickmick.ui.main.setting.SettingFragment
import com.sweetguyfanclub2th.mickmick.ui.main.todo.TodoFragment


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val number = intent?.getStringExtra("message")
        val poi = intent?.getStringExtra("poi")
        Log.d("메인 액티비티", number.toString())

        if (number != null && poi != null) {
            val fragment = TodoFragment()
            val bundle = Bundle()
            bundle.putString("message", number.toString())
            bundle.putString("poi", poi.toString())
            fragment.arguments = bundle

            supportFragmentManager.beginTransaction().add(R.id.fragment, fragment).commit()
            binding.bottomNavigationView.selectedItemId = R.id.menu_todo
        }
        else {
            supportFragmentManager.beginTransaction().add(R.id.fragment, HomeFragment()).commit()
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_home -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, HomeFragment()).commit()
                }
                R.id.menu_friends -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, FriendFragment()).commit()
                }
                R.id.menu_todo -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, TodoFragment()).commit()
                }
                R.id.menu_search -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, SearchPlaceFragment()).commit()
                }
                R.id.menu_settings -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment, SettingFragment()).commit()
                }
            }

            true
        }
    }

    private var backPressedTime : Long = 0
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        Log.d("TAG", "뒤로가기")

        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finish()
            return
        }

        Toast.makeText(this, "'뒤로' 버튼을 한번 더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }

    fun changeToSearchFragment(){
        supportFragmentManager.beginTransaction().replace(R.id.fragment, SearchPlaceFragment()).commit()
        binding.bottomNavigationView.selectedItemId = R.id.menu_search
    }

    @SuppressLint("CommitPrefEdits")
    fun editTextSaving(
        todoName: EditText?, editDate: EditText?,
        editTime: EditText?, editFriend: EditText?, editPlace: EditText?, selectTime : String
    ){
        val pref: SharedPreferences = getSharedPreferences("pref",0)
        val editor: SharedPreferences.Editor = pref.edit()

        if(!todoName?.text.isNullOrEmpty()) editor.putString("todoName", todoName?.text.toString()).apply()
        if(!editDate?.text.isNullOrEmpty()) editor.putString("editDate", editDate?.text.toString()).apply()
        if(!editTime?.text.isNullOrEmpty()) editor.putString("editTime", editTime?.text.toString()).apply()
        if(!editFriend?.text.isNullOrEmpty()) editor.putString("editFriend", editFriend?.text.toString()).apply()
        if(!editPlace?.text.isNullOrEmpty()) editor.putString("editPlace", editPlace?.text.toString()).apply()
        if(selectTime.isNotEmpty()) editor.putString("selectTime", selectTime).apply()
    }

    @SuppressLint("CommitPrefEdits")
    fun savingTextShow(
        todoName: EditText?, editDate: EditText?,
        editTime: EditText?, editFriend: EditText?, editPlace: EditText?
    ): String? {
        val pref: SharedPreferences = getSharedPreferences("pref",0)
        val editor: SharedPreferences.Editor = pref.edit()
        val time = pref.getString("selectTime", null)

        todoName?.setText(pref.getString("todoName", null))
        editDate?.setText(pref.getString("editDate", null))
        editTime?.setText(pref.getString("editTime", null))
        editFriend?.setText(pref.getString("editFriend", null))
        editPlace?.setText(pref.getString("editPlace", null))

        editor.clear().apply()
        return time
    }
}