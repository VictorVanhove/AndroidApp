package com.example.dikkeploaten.Services

import com.example.dikkeploaten.Models.Album
import com.example.dikkeploaten.Models.User

class Cache {
    var user: User = User(username= "", email= "", plates= arrayListOf(), wantList= arrayListOf())
    var albums: ArrayList<Album> = arrayListOf()
}