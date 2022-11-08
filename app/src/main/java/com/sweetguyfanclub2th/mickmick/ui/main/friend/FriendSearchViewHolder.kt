package com.sweetguyfanclub2th.mickmick.ui.main.friend

import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.data.FriendSearch
import com.sweetguyfanclub2th.mickmick.databinding.FriendSearchLayoutBinding

class FriendSearchViewHolder(private val binding: FriendSearchLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(friendSearch: FriendSearch){
        binding.nickname.text = friendSearch.nickname
        binding.email1.text = friendSearch.email
    }
}