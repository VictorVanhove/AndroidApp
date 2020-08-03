package com.hogent.domain.sources

import com.hogent.domain.models.Album

interface NetworkSource {

    suspend fun getAlbumList(): List<Album>
}