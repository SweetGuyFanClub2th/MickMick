package com.sweetguyfanclub2th.mickmick.ui.main.search.name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.data.searchpois.Poi
import com.sweetguyfanclub2th.mickmick.databinding.ItemSearchBinding

class NameAdapter(private val postList: ArrayList<Poi>) : RecyclerView.Adapter<NameViewHolder>() {
    private lateinit var binding: ItemSearchBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        binding = ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val addressResult = postList[position].newAddressList.newAddress
        holder.bind(postList[position], addressResult[0])
    }

    override fun getItemCount(): Int = postList.size
}