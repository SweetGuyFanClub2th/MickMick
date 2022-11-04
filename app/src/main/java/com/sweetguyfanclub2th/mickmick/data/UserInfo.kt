package com.sweetguyfanclub2th.mickmick.data

data class UserInfo(
    val nickname : String? = null,
    val friends : ArrayList<String>? = null,
    val todoId : ArrayList<String>? = null,
    val iconType : String = "Default",
    val favoritePlace : ArrayList<String>? = null
)
