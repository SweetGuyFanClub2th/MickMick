package com.sweetguyfanclub2th.mickmick.ui.main.search.name

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.data.searchpois.NewAddres
import com.sweetguyfanclub2th.mickmick.data.searchpois.Poi
import com.sweetguyfanclub2th.mickmick.databinding.ItemSearchBinding
import com.sweetguyfanclub2th.mickmick.ui.main.search.name.detail.DetailNameActivity

class NameViewHolder(private val binding: ItemSearchBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(itemPoi: Poi, itemAddress: NewAddres, context: Context){
        binding.itemName.text = itemPoi.name
        binding.itemMiddleBizName.text = itemPoi.middleBizName
        binding.itemFullAdressRoad.text = itemAddress.fullAddressRoad

        itemView.setOnClickListener {
            Intent(context, DetailNameActivity::class.java).apply {
                putExtra("id", itemPoi.id)
                putExtra("data", itemPoi.name)
                putExtra("fullAddressRoad", itemAddress.fullAddressRoad)
                putExtra("lat", itemPoi.noorLat)
                putExtra("lon", itemPoi.noorLon)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }.run { context.startActivity(this) }
        }
    }
}