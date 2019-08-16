package com.hogent.dikkeploaten.Models

data class User (
    var id: String = "",
    var username: String = "",
    var email: String = "",
    var plates: ArrayList<UserAlbum> = arrayListOf(),
    var wantList: ArrayList<UserAlbum> = arrayListOf()
)