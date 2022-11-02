package com.sweetguyfanclub2th.mickmick.ui.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.R

class WelcomeViewHolder(private var pageList: ArrayList<PageItem>):RecyclerView.Adapter<WelcomeViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WelcomeViewHolder {
        return WelcomeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_intro_pager_item, parent, false))
    }

    override fun getItemCount(): Int {
        return pageList.size
    }

    override fun onBindViewHolder(holder: WelcomeViewHolder, position: Int) {
        holder.bindWithView(pageList[position])
    }

    private fun bindWithView(any: Any) {
        // TODO
    }
}