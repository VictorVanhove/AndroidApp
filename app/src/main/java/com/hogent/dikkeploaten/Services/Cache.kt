package com.hogent.dikkeploaten.services

import com.hogent.dikkeploaten.models.Album
import com.hogent.dikkeploaten.models.User

class Cache {
    var user: User = User(username= "", email= "", plates= arrayListOf(), wantList= arrayListOf())
    var albums: ArrayList<Album> = arrayListOf()
}