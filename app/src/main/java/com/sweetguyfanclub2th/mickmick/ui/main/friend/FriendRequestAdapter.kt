package com.sweetguyfanclub2th.mickmick.ui.main.friend

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestore.getInstance
import com.google.firebase.ktx.Firebase
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.FriendRequest
import com.sweetguyfanclub2th.mickmick.databinding.FriendRequestLayoutBinding
import com.sweetguyfanclub2th.mickmick.databinding.FriendSearchLayoutBinding

class FriendRequestAdapter(val itemList: ArrayList<FriendRequest> = arrayListOf()) : RecyclerView.Adapter<FriendRequestViewHolder>() {
    private var firestore: FirebaseFirestore? = null
    private lateinit var binding: FriendRequestLayoutBinding
    private lateinit var db: FirebaseFirestore

    init {
        firestore = FirebaseFirestore.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendRequestViewHolder{

        binding =
            FriendRequestLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FriendRequestViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }




        override fun onBindViewHolder(holder: FriendRequestViewHolder, position: Int) {


        holder.bind(itemList[position])
        binding.friendPlus1.setOnClickListener {
            val myEmail = Firebase.auth.currentUser?.email.toString()

            val userInfo = db.collection(myEmail).document("userinfo")
            userInfo.get().addOnSuccessListener {
                val requestedEmail = it.get("friendRequest").toString()
                db.collection(myEmail).document("userinfo").update("friend",requestedEmail)
                db.collection(myEmail).document("userinfo").update("friendRequest",null)

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


