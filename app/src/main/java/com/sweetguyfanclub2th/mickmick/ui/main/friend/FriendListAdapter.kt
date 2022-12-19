package com.sweetguyfanclub2th.mickmick.ui.main.friend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestore.getInstance
import com.google.firebase.ktx.Firebase
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.FriendList
import com.sweetguyfanclub2th.mickmick.databinding.FriendListLayoutBinding

class FriendListAdapter(val itemList: ArrayList<FriendList> = arrayListOf()) :
    RecyclerView.Adapter<FriendListViewHolder>() {
    private var firestore: FirebaseFirestore? = null
    private lateinit var binding: FriendListLayoutBinding
    private lateinit var db: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendListViewHolder {

        binding =
            FriendListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }


    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.bind(itemList[position])
        binding.friendDelete2.setOnClickListener {
            val myEmail = Firebase.auth.currentUser?.email.toString()
            val userInfo = db.collection(myEmail).document("userinfo")
            userInfo.get().addOnSuccessListener {
                val friendEmail = itemList[position].email2.toString()
                itemList[position].email2

                db.collection(myEmail).document("userinfo").update("friend", FieldValue.arrayRemove(friendEmail))
                db.collection(friendEmail).document("userinfo").update("friend", FieldValue.arrayRemove(myEmail))

            }

        }
    }

    //val myEmail = Firebase.auth.currentUser?.email.toString()
    //holder.

    /*val myEmail = Firebase.auth.currentUser?.email.toString()
    binding.friendPlus1.setOnClickListener {
        Log.e("e","e")
        db.collection(myEmail).document("userinfo").update("friend",myEmail)
    }*/


}



