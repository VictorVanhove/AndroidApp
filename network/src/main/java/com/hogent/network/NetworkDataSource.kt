package com.hogent.network

class NetworkDataSource internal constructor(private val service: ApiService) {

    suspend fun getAlbumList() = service.getAlbumList()
}