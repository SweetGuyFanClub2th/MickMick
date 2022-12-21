package com.sweetguyfanclub2th.mickmick.ui.main.friend

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.FriendList
import com.sweetguyfanclub2th.mickmick.databinding.FragmentScheduleBinding
import com.sweetguyfanclub2th.mickmick.databinding.FriendListLayoutBinding
import com.sweetguyfanclub2th.mickmick.ui.main.MainActivity
import com.sweetguyfanclub2th.mickmick.ui.main.todo.ScheduleFragment
import kotlin.coroutines.coroutineContext

class FriendListAdapter(val itemList: ArrayList<FriendList> = arrayListOf()) :
    RecyclerView.Adapter<FriendListViewHolder>() {
    private var firestore: FirebaseFirestore? = null
    private lateinit var binding: FriendListLayoutBinding

    private lateinit var db: FirebaseFirestore

    val myEmail1 = Firebase.auth.currentUser?.email.toString()
    var friends = "멤버 : $myEmail1/"
    init {
        firestore = FirebaseFirestore.getInstance()
        db = FirebaseFirestore.getInstance()
    }



        override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
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



            val builder = AlertDialog.Builder(this.binding.friendDelete2.context)
            builder.setTitle("친구").setMessage("친구목록에서 삭제되었습니다.")
            val alertDialog = builder.create()
            alertDialog.show()

            val myEmail = Firebase.auth.currentUser?.email.toString()
            val userInfo = db.collection(myEmail).document("userinfo")
            userInfo.get().addOnSuccessListener {
                val friendEmail = itemList[position].email2.toString()
                itemList[position].email2

                db.collection(myEmail).document("userinfo").update("friend", FieldValue.arrayRemove(friendEmail))
                db.collection(friendEmail).document("userinfo").update("friend", FieldValue.arrayRemove(myEmail))

            }

        }


        binding.friendAdd.setOnClickListener {
            val friendEmail = itemList[position].email2.toString()


            val myEmail = Firebase.auth.currentUser?.email.toString()
            val userInfo = db.collection(myEmail).document("userinfo")

            userInfo.get().addOnSuccessListener {
                val friendEmail = itemList[position].email2.toString()
                //val myEmail1 = it.get("nickname")
                itemList[position].email2
                val builder = AlertDialog.Builder(this.binding.friendDelete2.context)
                builder.setTitle("추가").setMessage("일정에 친구가 추가되었습니다.")
                val alertDialog = builder.create()
                alertDialog.show()

                friends = "$friends $friendEmail/"

                val myEmail = Firebase.auth.currentUser?.email.toString()
                val userInfo = db.collection(myEmail).document("userinfo")
                userInfo.update("cacheFriend", friends)


}

}
}

}



