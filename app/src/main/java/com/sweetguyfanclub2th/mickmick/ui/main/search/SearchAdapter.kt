package com.sweetguyfanclub2th.mickmick.ui.main.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.data.searchpois.Poi
import com.sweetguyfanclub2th.mickmick.databinding.ItemSearchBinding

class SearchAdapter(private val postList: ArrayList<Poi>) : RecyclerView.Adapter<SearchViewHolder>() {
    private lateinit var binding: ItemSearchBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val addressResult = postList[position].newAddressList.newAddress
        holder.bind(postList[position], addressResult[0])
    }

    override fun getItemCount(): Int = postList.size
}