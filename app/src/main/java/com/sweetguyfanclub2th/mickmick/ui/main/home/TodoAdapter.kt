package com.sweetguyfanclub2th.mickmick.ui.main.home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.data.TodoData

class TodoAdapter(private val recyclerViewItems: ArrayList<TodoData>):
    RecyclerView.Adapter<TodoAdapter.RecyclerViewViewHolder>()  {

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

        fun bind(recyclerViewItem: TodoData, i: Int) {

        }
    }
}