package com.sweetguyfanclub2th.mickmick.ui.main.setting

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.R

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
            }
        }

    }
}