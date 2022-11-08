package com.sweetguyfanclub2th.mickmick.ui.main.friend

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.data.FriendSearch
import com.sweetguyfanclub2th.mickmick.databinding.FriendSearchLayoutBinding

class FriendSearchAdapter : RecyclerView.Adapter<FriendSearchViewHolder>() {
    private var firestore: FirebaseFirestore? = null
    private lateinit var binding: FriendSearchLayoutBinding

    // Person 클래스 ArrayList 생성성
    var userName: ArrayList<FriendSearch> = arrayListOf()

    init {  // telephoneBook의 문서를 불러온 뒤 Person으로 변환해 ArrayList에 담음
        firestore?.collection("test")
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                userName.clear()


                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(FriendSearch::class.java)
                    userName.add(item!!)
                }
                notifyDataSetChanged()
            }
    }

    // xml파일을 inflate하여 ViewHolder를 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendSearchViewHolder {
        binding =
            FriendSearchLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendSearchViewHolder(binding)
    }

    // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
    override fun onBindViewHolder(holder: FriendSearchViewHolder, position: Int) {
        holder.bind(userName[position])
    }

    // 리사이클러뷰의 아이템 총 개수 반환
    override fun getItemCount(): Int = userName.size

    fun search(searchWord: String, option: String) {
        firestore?.collection("test")
            ?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
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
}