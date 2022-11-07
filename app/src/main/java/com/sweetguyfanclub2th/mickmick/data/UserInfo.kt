package com.sweetguyfanclub2th.mickmick.data

data class UserInfo(
    val nickname : String? = null,
    val friend : ArrayList<String>? = null,
    val friendRequest : ArrayList<String>? = null,
    val todoId : ArrayList<String>? = null,
    val iconType : String = "Default",
    val favoritePlace : ArrayList<String>? = null
)
