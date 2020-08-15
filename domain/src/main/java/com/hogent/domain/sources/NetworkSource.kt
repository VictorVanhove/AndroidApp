package com.hogent.domain.sources

import com.hogent.domain.models.Album

/**
 * This interface contains all network operations, used in network module.
 */
interface NetworkSource {

    suspend fun getAlbumList(): List<Album>
}
