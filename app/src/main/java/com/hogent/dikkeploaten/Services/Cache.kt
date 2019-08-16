package com.hogent.dikkeploaten.Services

import com.hogent.dikkeploaten.Models.Album
import com.hogent.dikkeploaten.Models.User

class Cache {
    var user: User = User(username= "", email= "", plates= arrayListOf(), wantList= arrayListOf())
    var albums: ArrayList<Album> = arrayListOf()
}