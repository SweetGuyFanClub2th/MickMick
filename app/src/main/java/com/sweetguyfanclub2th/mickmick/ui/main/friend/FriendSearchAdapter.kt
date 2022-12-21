package com.sweetguyfanclub2th.mickmick.ui.main.friend

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sweetguyfanclub2th.mickmick.data.FriendSearch
import com.sweetguyfanclub2th.mickmick.databinding.FriendSearchLayoutBinding


@SuppressLint("NotifyDataSetChanged")
class FriendSearchAdapter : RecyclerView.Adapter<FriendSearchViewHolder>() {
    private var firestore: FirebaseFirestore? = null
    private lateinit var binding: FriendSearchLayoutBinding
    private lateinit var db: FirebaseFirestore

    // Person 클래스 ArrayList 생성성
    var userName: ArrayList<FriendSearch> = arrayListOf()
    var count = 0

    init {  // telephoneBook의 문서를 불러온 뒤 Person으로 변환해 ArrayList에 담음
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
                    val item = snapshot.toObject(FriendSearch::class.java)
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

    var searchCheck =0
    // xml파일을 inflate하여 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendSearchViewHolder {
        binding =
            FriendSearchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendSearchViewHolder(binding)
    }
    fun search(searchWord: String, option: String) {
        searchCheck = 0
        firestore?.collection("friendSearch")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            // ArrayList 비워줌
            userName.clear()

            for (snapshot in querySnapshot!!.documents) {
                if (snapshot.getString(option)!!.contains(searchWord)) {
                    val item = snapshot.toObject(FriendSearch::class.java)
                    userName.add(item!!)
                    searchCheck +=1
                }
            }

            notifyDataSetChanged()
        }
    }


    // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
    override fun onBindViewHolder(holder: FriendSearchViewHolder, position: Int) {
        holder.bind(userName[position])
        binding.friendPlus.setOnClickListener {
            val myEmail = Firebase.auth.currentUser?.email.toString()

            val builder = AlertDialog.Builder(this.binding.friendPlus.context)
            builder.setTitle("친구추가").setMessage("친구요청을 보냈습니다.")
            val alertDialog = builder.create()
            alertDialog.show()

            if(searchCheck == 0) {
                val otherEmail = userName[position].email.toString()

                db.collection(otherEmail).document("userinfo")
                    .update("friendRequest", FieldValue.arrayUnion(myEmail))
                count++
            }
            else if(searchCheck == 1) {
                val otherEmail = userName[0].email.toString()

                db.collection(otherEmail).document("userinfo")
                    .update("friendRequest", FieldValue.arrayUnion(myEmail))
                count++
            }

        }
    }

    // 리사이클러뷰의 아이템 총 개수 반환
    override fun getItemCount(): Int = userName.size
}