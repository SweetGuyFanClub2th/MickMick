package com.sweetguyfanclub2th.mickmick.ui.main.friend

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.FriendList
import com.sweetguyfanclub2th.mickmick.databinding.ActivityFriendlistBinding
import com.sweetguyfanclub2th.mickmick.databinding.FragmentScheduleBinding
import com.sweetguyfanclub2th.mickmick.databinding.FriendListLayoutBinding


class FriendListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendlistBinding    // 뷰 바인딩
    private lateinit var binding1: FriendListLayoutBinding    // 뷰 바인딩

    //private var _binding: FragmentScheduleBinding? = null
    //private val binding3 get() = _binding!!

    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
    val itemList = arrayListOf<FriendList>()    // 리스트 아이템 배열
    var plusfriend = String()
    val adapter = FriendListAdapter(itemList,plusfriend)         // 리사이클러 뷰 어댑터

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendlistBinding.inflate(layoutInflater)
        binding1 = FriendListLayoutBinding.inflate(layoutInflater)

        binding.backpress1.setOnClickListener {
            //binding3.editFriend.text to
            //var scFragment = ScheduleFragment()
            //var bundle = Bundle()
            //bundle.putInt("친구이름",19)
            //scFragment.arguments = bundle
            finish()



        }

        val view = binding.root
        setContentView(view)

        binding.recyclerview3.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview3.adapter = adapter


        val myEmail = Firebase.auth.currentUser?.email.toString()
        val userInfo = db.collection(myEmail).document("userinfo")
        var tv1: TextView? = null

        userInfo.get().addOnSuccessListener {
            //val intent = Intent(this, FriendRequestActivity::class.java)
            //intent.putExtra("친구이름","문자열 전달쓰")
            //startActivity(intent)



            Log.e("MyEmail", myEmail)

            db.collection("friendSearch")   // 작업할 컬렉션
                .get()      // 문서 가져오기
                .addOnSuccessListener { result ->
                    // 성공할 경우
                    itemList.clear()

                    for (document in result) {  // 가져온 문서들은 result에 들어감
                        val friendListEmail : List<String> = it.get("friend") as List<String>
                        val count3 = friendListEmail.size
                        Log.e("count3", count3.toString())
                        //binding.friendNumber.setText(count3)
                        tv1 = findViewById(R.id.friend_number)
                       tv1?.text = count3.toString()+"명"




                        var count = friendListEmail.size
                        val item = FriendList(
                            document["nickname"] as String, document["email"] as String
                        )
                        Log.e("myitem1", document["nickname"] as String)
                        Log.e("myitem2", document["email"] as String)

                        while(count != 0) {
                            --count
                        if (document["email"] as String == friendListEmail[count]) {
                            itemList.add(item)

                        }
                        }
                    }

                    adapter.notifyDataSetChanged()  // 리사이클러 뷰 갱신

                }



            /*fun friendRequest(requestEmail : String, myEmail : String){
                db.collection(myEmail).document("userinfo").update("friend", requestEmail)
                db.collection(requestEmail).document("userinfo").update("friend", myEmail)
            }*/

        }

    }

}

