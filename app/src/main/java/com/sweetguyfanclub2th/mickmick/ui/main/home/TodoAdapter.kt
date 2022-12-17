package com.sweetguyfanclub2th.mickmick.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.TodoData

class TodoAdapter(private val recyclerViewItems: ArrayList<TodoData>):
    RecyclerView.Adapter<TodoAdapter.TodoViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(recyclerViewItems[position], position)
    }

    override fun getItemCount(): Int {
        return recyclerViewItems.size
    }

    inner class TodoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var title: TextView = itemView.findViewById(R.id.todo_title)
        private var dateTime: TextView = itemView.findViewById(R.id.todo_date)
        private var member: TextView = itemView.findViewById(R.id.todo_member)
        private var place: TextView = itemView.findViewById(R.id.todo_place)

        @SuppressLint("SetTextI18n")
        fun bind(recyclerViewItem: TodoData, i: Int) {
            dateTime.text = recyclerViewItem.title
            title.text = recyclerViewItem.time
            member.text = recyclerViewItem.member
            place.text = recyclerViewItem.place
        }
    }
}