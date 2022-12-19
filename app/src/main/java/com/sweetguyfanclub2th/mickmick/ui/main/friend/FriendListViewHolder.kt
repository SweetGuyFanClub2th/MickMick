package com.sweetguyfanclub2th.mickmick.ui.main.friend

import androidx.recyclerview.widget.RecyclerView
import com.sweetguyfanclub2th.mickmick.data.FriendList
import com.sweetguyfanclub2th.mickmick.databinding.FriendListLayoutBinding

class FriendListViewHolder(private val binding: FriendListLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(friendList: FriendList){
        binding.nickname2.text = friendList.nickname2
        binding.email2.text = friendList.email2
    }

}