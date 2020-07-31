package com.hogent.dikkeploaten.repositories

import com.hogent.database.UserAlbum
import com.hogent.database.UserAlbumDao

class UserAlbumRepository private constructor(
    private val userAlbumDao: com.hogent.database.UserAlbumDao
) {

    suspend fun createUserAlbum(albumId: String, albumType: String) {
        val userAlbum = com.hogent.database.UserAlbum(albumId, albumType)
        userAlbumDao.insertUserAlbum(userAlbum)
    }

    suspend fun removeUserAlbum(userAlbum: com.hogent.database.UserAlbum) {
        userAlbumDao.deleteUserAlbum(userAlbum)
    }

    suspend fun getUserAlbum(albumId: String): com.hogent.database.UserAlbum = userAlbumDao.getUserAlbum(albumId)

    fun isInCollection(albumId: String) =
        userAlbumDao.isInCollection(albumId)

    fun getCollectionUser() = userAlbumDao.getAlbumsUser("collection")
    fun getWantlistUser() = userAlbumDao.getAlbumsUser("wantlist")

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: UserAlbumRepository? = null

        fun getInstance(userAlbumDao: com.hogent.database.UserAlbumDao) =
            instance ?: synchronized(this) {
                instance ?: UserAlbumRepository(userAlbumDao).also { instance = it }
            }
    }
}