package com.hogent.dikkeploaten.models

data class Album (
    var id: String = "",
    var title: String = "",
    var artist: String = "",
    var thumb: String = "",
    var description: String = "",
    var genre: String = "",
    var released_in: String = "",
    var tracklist: String = "",
    var musicians: String = "",
    var images: ArrayList<String> = arrayListOf()
)