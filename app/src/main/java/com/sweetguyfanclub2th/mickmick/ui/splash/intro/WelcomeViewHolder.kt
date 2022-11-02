package com.sweetguyfanclub2th.mickmick.ui.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.R
import com.sweetguyfanclub2th.mickmick.ui.splash.intro.PageItem

class MyIntroPagerRecyclerAdapter(private var pageList: ArrayList<PageItem>):RecyclerView.Adapter<MyIntroPagerRecyclerAdapter.WelcomeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeViewHolder {
        return WelcomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.activity_welcome, parent, false))
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    override fun onBindViewHolder(holder: WelcomeViewHolder, position: Int) {
        holder.bindWithView(pageList[position])
    }

    inner class WelcomeViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindWithView(pageItem: PageItem) {
            itemView.setBackgroundColor(pageItem.color)
        }
    }
}