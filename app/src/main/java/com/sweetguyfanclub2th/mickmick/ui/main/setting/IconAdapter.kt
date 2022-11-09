package com.sweetguyfanclub2th.mickmick.ui.main.setting

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.ui.main.MainActivity

class IconAdapter(private val recyclerViewItems: ArrayList<IconData>): RecyclerView.Adapter<IconAdapter.RecyclerViewViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.icon_items, parent, false)
        return RecyclerViewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bind(recyclerViewItems[position], position)
    }

    override fun getItemCount(): Int {
        return recyclerViewItems.size
    }

    inner class RecyclerViewViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val img: ImageView = itemView.findViewById(R.id.img_view)
        private val title: TextView = itemView.findViewById(R.id.title_txt)
        private val desc: TextView = itemView.findViewById(R.id.desc_text)

        fun bind(recyclerViewItem: IconData, i: Int) {

            title.text = recyclerViewItem.title
            desc.text = recyclerViewItem.desc

            itemView.setOnClickListener {
                Log.d("num", i.toString())
                changeIconTheme(i)
            }
        }

        fun changeIconTheme(theme: Int) {
            val email = FirebaseAuth.getInstance().currentUser?.email.toString()
            var db: FirebaseFirestore = FirebaseFirestore.getInstance()
            val userInfo = db.collection(email).document("userinfo")
            var type: String = "default"

            when (theme) {
                0 -> type = "red"
                1 -> type = "yellow"
                2 -> type = "default"
                3 -> type = "blue"
                4 -> type = "purple"
                5 -> type = "gray"
            }

            userInfo.update("iconType", type) // 업데이트
                .addOnSuccessListener {
                    Log.d("icon", "iconType 업데이트 성공")
                    val intent = Intent(itemView.context, SettingFragment::class.java)
                    itemView.context.startActivity(intent)
                }
        }
    }
}