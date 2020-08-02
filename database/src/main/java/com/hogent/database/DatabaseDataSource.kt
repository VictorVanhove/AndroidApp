package com.hogent.database

import com.hogent.database.dao.AlbumDao
import com.hogent.database.dao.UserAlbumDao
import com.hogent.database.models.DatabaseAlbum
import com.hogent.database.models.DatabaseUserAlbum

private const val COLLECTION_TYPE = "collection"
private const val WANTLIST_TYPE = "wantlist"

class DatabaseDataSource internal constructor(
    private val albumDao: AlbumDao,
    private val userAlbumDao: UserAlbumDao
) {

    //region Albums

    suspend fun getAlbumList(): List<DatabaseAlbum> = albumDao.getAlbumList()

    suspend fun getAlbumWithId(id: String): DatabaseAlbum = albumDao.getAlbumWithId(id)

    suspend fun insertAllAlbums(albums: List<DatabaseAlbum>) = albumDao.insertAllAlbums(albums)

    //endregion

    //region User Albums

    suspend fun createUserAlbum(albumId: String, albumType: String) {
        val userAlbum = DatabaseUserAlbum(albumId, albumType)
        userAlbumDao.insertUserAlbum(userAlbum)
    }

    suspend fun removeUserAlbum(userAlbum: DatabaseUserAlbum) {
        userAlbumDao.deleteUserAlbum(userAlbum)
    }

    suspend fun getUserAlbum(albumId: String): DatabaseUserAlbum = userAlbumDao.getUserAlbum(albumId)

    suspend fun isInCollection(albumId: String) =
        userAlbumDao.isInCollection(albumId)

    suspend fun getCollectionUser() = userAlbumDao.getAlbumsUser(COLLECTION_TYPE)

    suspend fun getWantlistUser() = userAlbumDao.getAlbumsUser(WANTLIST_TYPE)

    //endregion
}