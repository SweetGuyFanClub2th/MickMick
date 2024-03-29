package com.sweetguyfanclub2th.mickmick.ui.main.search

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.data.searchpois.NewAddres
import com.sweetguyfanclub2th.mickmick.data.searchpois.Poi
import com.sweetguyfanclub2th.mickmick.databinding.ItemSearchBinding
import com.sweetguyfanclub2th.mickmick.ui.main.search.detail.ShowDetailInfoActivity

class SearchPlaceAdapter(val context: Context, private val postList: ArrayList<Poi>) : RecyclerView.Adapter<SearchPlaceAdapter.NameViewHolder>() {
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

    inner class NameViewHolder(private val binding: ItemSearchBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(itemPoi: Poi, itemAddress: NewAddres){
            binding.itemName.text = itemPoi.name
            binding.itemMiddleBizName.text = itemPoi.middleBizName
            binding.itemFullAddressRoad.text = itemAddress.fullAddressRoad

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ShowDetailInfoActivity::class.java)

                intent.putExtra("id", itemPoi.id)
                intent.putExtra("name", itemPoi.name)
                intent.putExtra("fullAddressRoad", itemAddress.fullAddressRoad)
                intent.putExtra("lat", itemPoi.noorLat)
                intent.putExtra("lon", itemPoi.noorLon)

                ContextCompat.startActivity(itemView.context, intent, null)
            }
        }
    }
}