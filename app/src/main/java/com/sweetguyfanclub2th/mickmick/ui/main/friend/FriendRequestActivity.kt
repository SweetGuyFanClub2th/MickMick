package com.sweetguyfanclub2th.mickmick.ui.main.friend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.FriendRequest
import com.sweetguyfanclub2th.mickmick.data.UserInfo
import com.sweetguyfanclub2th.mickmick.databinding.ActivityFriendrequestBinding
import com.sweetguyfanclub2th.mickmick.databinding.FriendRequestLayoutBinding
import com.sweetguyfanclub2th.mickmick.databinding.FriendSearchLayoutBinding

class FriendRequestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFriendrequestBinding    // 뷰 바인딩
    private lateinit var binding1: FriendRequestLayoutBinding    // 뷰 바인딩

    val db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언
    val itemList = arrayListOf<FriendRequest>()    // 리스트 아이템 배열
    val adapter = FriendRequestAdapter(itemList)         // 리사이클러 뷰 어댑터

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendrequestBinding.inflate(layoutInflater)
        binding1 = FriendRequestLayoutBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        binding.recyclerview2.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerview2.adapter = adapter


        val myEmail = Firebase.auth.currentUser?.email.toString()
        val userInfo = db.collection(myEmail).document("userinfo")


        userInfo.get().addOnSuccessListener {
            val requestedEmail = it.get("friendRequest").toString()

            Log.e("MyEmail", myEmail)
            Log.e("RequestedEmail", requestedEmail)

            db.collection("friendSearch")   // 작업할 컬렉션
                .get()      // 문서 가져오기
                .addOnSuccessListener { result ->
                    // 성공할 경우
                    itemList.clear()
                    for (document in result) {  // 가져온 문서들은 result에 들어감
                        val item = FriendRequest(
                            document["nickname"] as String, document["email"] as String
                        )
                        Log.e("myitem1", document["nickname"] as String)
                        Log.e("myitem2", document["email"] as String)

                        if (document["email"] as String == requestedEmail) {
                            itemList.add(item)

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

