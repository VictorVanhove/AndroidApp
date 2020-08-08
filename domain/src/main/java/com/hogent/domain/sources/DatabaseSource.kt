package com.hogent.domain.sources

import com.hogent.domain.models.Album
import com.hogent.domain.models.AlbumAndUserAlbums
import com.hogent.domain.models.UserAlbum

/**
 * This interface contains all database operations, used in view module.
 */
interface DatabaseSource {

    suspend fun getAlbumList(): List<Album>

    suspend fun getAlbumWithId(id: String): Album

    suspend fun insertAllAlbums(albums: List<Album>)

    suspend fun createUserAlbum(albumId: String, albumType: String)

    suspend fun removeUserAlbum(userAlbum: UserAlbum)

    suspend fun getUserAlbum(albumId: String): UserAlbum

    suspend fun isInCollection(albumId: String): Boolean

    suspend fun getCollectionUser(): List<AlbumAndUserAlbums>

    suspend fun getWantlistUser(): List<AlbumAndUserAlbums>
}