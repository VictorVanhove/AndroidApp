package com.hogent.database

import com.hogent.database.dao.AlbumDao
import com.hogent.database.dao.UserAlbumDao
import com.hogent.database.models.*
import com.hogent.database.models.toAlbum
import com.hogent.database.models.toDatabaseAlbum
import com.hogent.domain.models.Album
import com.hogent.domain.models.UserAlbum
import com.hogent.domain.sources.DatabaseSource

private const val COLLECTION_TYPE = "collection"
private const val WANTLIST_TYPE = "wantlist"

class DatabaseDataSource internal constructor(
    private val albumDao: AlbumDao,
    private val userAlbumDao: UserAlbumDao
): DatabaseSource {

    //region Albums

    override suspend fun getAlbumList(): List<Album> = albumDao.getAlbumList().map { it.toAlbum() }

    override suspend fun getAlbumWithId(id: String): Album = albumDao.getAlbumWithId(id).toAlbum()

    override suspend fun insertAllAlbums(albums: List<Album>) = albumDao.insertAllAlbums(albums.map { it.toDatabaseAlbum() })

    //endregion

    //region User Albums

    override suspend fun createUserAlbum(albumId: String, albumType: String) {
        val userAlbum = DatabaseUserAlbum(albumId, albumType)
        userAlbumDao.insertUserAlbum(userAlbum)
    }

    override suspend fun removeUserAlbum(userAlbum: UserAlbum) {
        userAlbumDao.deleteUserAlbum(userAlbum.albumId)
    }

    override suspend fun getUserAlbum(albumId: String): UserAlbum = userAlbumDao.getUserAlbum(albumId).toUserAlbum()

    override suspend fun isInCollection(albumId: String) =
        userAlbumDao.isInCollection(albumId)

    override suspend fun getCollectionUser() = userAlbumDao.getAlbumsUser(COLLECTION_TYPE).map { it.toAlbumAndUserAlbums() }

    override suspend fun getWantlistUser() = userAlbumDao.getAlbumsUser(WANTLIST_TYPE).map { it.toAlbumAndUserAlbums() }

    //endregion
}