package com.sweetguyfanclub2th.mickmick.ui.main.todo

data class TodoData (
    var title : String? = null,
    var userId : String? = null,
    var timeStamp : Long? = null,
    var time : String? = null,
    var place : String? = null,
    var participants : List<String> ? = null,
)