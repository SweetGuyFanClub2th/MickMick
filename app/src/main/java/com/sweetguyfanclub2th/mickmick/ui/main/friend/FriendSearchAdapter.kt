package com.sweetguyfanclub2th.mickmick.ui.main.friend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.FriendSearch
import com.sweetguyfanclub2th.mickmick.databinding.FriendSearchLayoutBinding


class FriendSearchAdapter : RecyclerView.Adapter<FriendSearchViewHolder>() {
    private var firestore: FirebaseFirestore? = null
    private lateinit var binding: FriendSearchLayoutBinding
    private lateinit var db: FirebaseFirestore

    // Person 클래스 ArrayList 생성성
    var userName: ArrayList<FriendSearch> = arrayListOf()

    var count = 0




    init {  // telephoneBook의 문서를 불러온 뒤 Person으로 변환해 ArrayList에 담음

        //userName.add(FriendSearch("roy","roy1109"))
        //userName.add(FriendSearch("kangmin","kangmin913"))


        firestore = FirebaseFirestore.getInstance()
        db = FirebaseFirestore.getInstance()
        firestore?.collection("friendSearch")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList 비워줌
            userName.clear()
            val myEmail = Firebase.auth.currentUser?.email.toString()
            val userInfo = db.collection(myEmail).document("userinfo")
            var help = 0
            userInfo.get().addOnSuccessListener {


                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(FriendSearch::class.java)

                    val friendListEmail: List<String> = it.get("friend") as List<String>


                    var count = friendListEmail.size
                    var check = 0
                    if((count>0)&&(help<count)) {
                        while (count != 0) {
                            --count
                            if ((item?.email.equals(friendListEmail[count]))) {
                                ++help
                                ++check
                                break
                            }
                        }
                    }

                    if(check != 0)
                    {
                        continue
                    }
                    if ((item?.email.equals(myEmail))) {
                        continue
                    }
                     // 본인은 친구창에서 제외
                    userName.add(item!!)
                    Log.e("userName2", userName.toString())



                }
                notifyDataSetChanged()
            }
        }



    }

    // xml파일을 inflate하여 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendSearchViewHolder {
        binding =
            FriendSearchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendSearchViewHolder(binding)
    }
    fun search(searchWord: String, option: String) {
        firestore?.collection("friendSearch")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList 비워줌
            userName.clear()

            for (snapshot in querySnapshot!!.documents) {
                if (snapshot.getString(option)!!.contains(searchWord)) {
                    var item = snapshot.toObject(FriendSearch::class.java)
                    userName.add(item!!)
                }
            }
            notifyDataSetChanged()
        }
    }

    // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
    override fun onBindViewHolder(holder: FriendSearchViewHolder, position: Int) {

        //var viewHolder = (holder as FriendSearchViewHolder).itemView
        holder.bind(userName[position])


        //Log.e("test", userName[position].email.toString())
        binding.friendPlus.setOnClickListener {
            //binding.friendPlus.visibility = INVISIBLE
            val myEmail = Firebase.auth.currentUser?.email.toString()
            val otherEmail = userName[position].email.toString()
            Log.e("test01", myEmail)
            Log.e("test02", otherEmail)

            db.collection(otherEmail).document("userinfo").update("friendRequest",FieldValue.arrayUnion(myEmail))
                .addOnSuccessListener {
                    Log.e("success", myEmail)
                }
                .addOnFailureListener {
                    Log.e("Fail", myEmail)
                }
            count++
        }
       //holder.itemView.setOnClickListener{

            //Log.e("test", userName.get(2).toString())
        //}
    }

    // 리사이클러뷰의 아이템 총 개수 반환
    override fun getItemCount(): Int = userName.size



}