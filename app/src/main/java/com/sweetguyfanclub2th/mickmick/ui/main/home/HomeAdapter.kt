package com.sweetguyfanclub2th.mickmick.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.TodoData
import com.sweetguyfanclub2th.mickmick.ui.main.search.detail.SearchDetailInfoActivity

class HomeAdapter(private val recyclerViewItems: ArrayList<TodoData>):
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return HomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(recyclerViewItems[position])
    }

    override fun getItemCount(): Int {
        return recyclerViewItems.size
    }

    inner class HomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.todo_title)
        private var dateTime: TextView = itemView.findViewById(R.id.todo_date)
        private var member: TextView = itemView.findViewById(R.id.todo_member)
        private var place: TextView = itemView.findViewById(R.id.todo_place)

        @SuppressLint("SetTextI18n")
        fun bind(recyclerViewItem: TodoData) {
            dateTime.text = recyclerViewItem.title
            title.text = recyclerViewItem.time
            member.text = recyclerViewItem.member
            place.text = recyclerViewItem.place

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, SearchDetailInfoActivity::class.java)
//
//                intent.putExtra("id", itemPoi.id)
//                intent.putExtra("name", itemPoi.name)
//                intent.putExtra("fullAddressRoad", itemAddress.fullAddressRoad)
//                intent.putExtra("lat", itemPoi.noorLat)
//                intent.putExtra("lon", itemPoi.noorLon)

                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }
}