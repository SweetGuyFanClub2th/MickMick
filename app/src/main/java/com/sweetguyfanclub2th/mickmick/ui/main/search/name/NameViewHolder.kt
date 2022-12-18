package com.sweetguyfanclub2th.mickmick.ui.main.search.name

import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.data.searchpois.NewAddres
import com.sweetguyfanclub2th.mickmick.data.searchpois.Poi
import com.sweetguyfanclub2th.mickmick.databinding.ItemSearchBinding

class NameViewHolder(private val binding: ItemSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(itemPoi: Poi, itemAddress: NewAddres){
        binding.itemName.text = itemPoi.name
        binding.itemMiddleBizName.text = itemPoi.middleBizName
        binding.itemFullAdressRoad.text = itemAddress.fullAddressRoad

    }
}