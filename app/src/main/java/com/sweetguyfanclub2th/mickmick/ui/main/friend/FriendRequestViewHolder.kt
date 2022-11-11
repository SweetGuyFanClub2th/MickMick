package com.sweetguyfanclub2th.mickmick.ui.main.friend

import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.data.FriendRequest
import com.sweetguyfanclub2th.mickmick.data.FriendSearch
import com.sweetguyfanclub2th.mickmick.databinding.FriendRequestLayoutBinding

class FriendRequestViewHolder(private val binding: FriendRequestLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {


    fun bind(friendRequest: FriendRequest){
        binding.nickname1.text = friendRequest.nickname1
        binding.email1.text = friendRequest.email1
    }

}