package com.hogent.dikkeploaten.models

/**
 * Model class for user.
 */
data class User(
    var id: String = "",
    var username: String = "",
    var email: String = "",
    var plates: ArrayList<UserAlbum> = arrayListOf(),
    var wantList: ArrayList<UserAlbum> = arrayListOf()
)