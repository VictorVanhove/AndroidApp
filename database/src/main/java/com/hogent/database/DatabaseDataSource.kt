package com.hogent.database

import androidx.lifecycle.LiveData
import com.hogent.database.dao.AlbumDao
import com.hogent.database.dao.UserAlbumDao
import com.hogent.database.models.DatabaseAlbum
import com.hogent.database.models.UserAlbum

private const val COLLECTION_TYPE = "collection"
private const val WANTLIST_TYPE = "wantlist"

// TODO: Make all functions suspend instead of returning LiveData.
class DatabaseDataSource internal constructor(
    private val albumDao: AlbumDao,
    private val userAlbumDao: UserAlbumDao
) {

    //region Albums

    fun getAlbumList(): LiveData<List<DatabaseAlbum>> = albumDao.getAlbumList()

    fun getAlbumWithId(id: String): LiveData<DatabaseAlbum> = albumDao.getAlbumWithId(id)

    suspend fun insertAllAlbums(albums: List<DatabaseAlbum>) = albumDao.insertAllAlbums(albums)

    //endregion

    //region User Albums

    suspend fun createUserAlbum(albumId: String, albumType: String) {
        val userAlbum = UserAlbum(albumId, albumType)
        userAlbumDao.insertUserAlbum(userAlbum)
    }

    suspend fun removeUserAlbum(userAlbum: UserAlbum) {
        userAlbumDao.deleteUserAlbum(userAlbum)
    }

    suspend fun getUserAlbum(albumId: String): UserAlbum = userAlbumDao.getUserAlbum(albumId)

    fun isInCollection(albumId: String) =
        userAlbumDao.isInCollection(albumId)

    fun getCollectionUser() = userAlbumDao.getAlbumsUser(COLLECTION_TYPE)

    fun getWantlistUser() = userAlbumDao.getAlbumsUser(WANTLIST_TYPE)

    //endregion
}