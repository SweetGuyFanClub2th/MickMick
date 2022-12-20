package com.sweetguyfanclub2th.mickmick.ui.main.friend

import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestore.getInstance
import com.google.firebase.ktx.Firebase
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.FriendRequest
import com.sweetguyfanclub2th.mickmick.databinding.FriendRequestLayoutBinding
import com.sweetguyfanclub2th.mickmick.databinding.FriendSearchLayoutBinding

class FriendRequestAdapter(val itemList: ArrayList<FriendRequest> = arrayListOf()) :
    RecyclerView.Adapter<FriendRequestViewHolder>() {
    private var firestore: FirebaseFirestore? = null
    private lateinit var binding: FriendRequestLayoutBinding
    private lateinit var db: FirebaseFirestore
    private val data = arrayListOf<FriendRequest>()

    init {
        firestore = FirebaseFirestore.getInstance()
        db = FirebaseFirestore.getInstance()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FriendRequestViewHolder {

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

            val builder = AlertDialog.Builder(this.binding.friendPlus1.context)
            builder.setTitle("친구요청").setMessage("친구요청을 수락하셨습니다.")
            val alertDialog = builder.create()
            alertDialog.show()

            val myEmail = Firebase.auth.currentUser?.email.toString()
            val userInfo = db.collection(myEmail).document("userinfo")
            userInfo.get().addOnSuccessListener {
                //val requestedEmail = it.get("friendRequest").toString()
                //val requestedEmail : List<String> = it.get("friendRequest") as List<String>
                val requestedEmail = itemList[position].email1.toString()
                itemList[position].email1

                db.collection(myEmail).document("userinfo").update("friend", FieldValue.arrayUnion(requestedEmail))
                db.collection(myEmail).document("userinfo").update("friend", FieldValue.arrayUnion(requestedEmail))
                db.collection(myEmail).document("userinfo").update("friendRequest", FieldValue.arrayRemove(requestedEmail))
                db.collection(requestedEmail).document("userinfo").update("friendRequest", FieldValue.arrayRemove(myEmail))
                db.collection(requestedEmail).document("userinfo").update("friend", FieldValue.arrayUnion(myEmail))

            }

        }

        binding.friendDelete1.setOnClickListener {

            val builder = AlertDialog.Builder(this.binding.friendPlus1.context)
            builder.setTitle("친구요청").setMessage("친구요청을 거절하셨습니다.")
            val alertDialog = builder.create()
            alertDialog.show()

            val myEmail = Firebase.auth.currentUser?.email.toString()
            val userInfo = db.collection(myEmail).document("userinfo")
            userInfo.get().addOnSuccessListener {
                val requestedEmail = itemList[position].email1.toString()
                itemList[position].email1

                db.collection(myEmail).document("userinfo").update("friendRequest", FieldValue.arrayRemove(requestedEmail))
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



