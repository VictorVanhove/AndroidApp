package com.hogent.network

import com.hogent.domain.sources.NetworkSource
import com.hogent.network.models.toAlbum

class NetworkDataSource internal constructor(private val service: ApiService) : NetworkSource {

    override suspend fun getAlbumList() = service.getAlbumList().map { it.toAlbum() }
}
