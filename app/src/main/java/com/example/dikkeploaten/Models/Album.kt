package com.example.dikkeploaten.Models

class Album {
    var id: Int
    var title: String
    var artist: String
    var image: String

    constructor(id: Int, title: String, artist: String, image: String) {
        this.id = id
        this.title = title
        this.artist = artist
        this.image = image
    }
}