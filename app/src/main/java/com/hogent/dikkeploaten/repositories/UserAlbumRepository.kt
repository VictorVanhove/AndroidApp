package com.hogent.dikkeploaten.repositories

import com.hogent.dikkeploaten.database.UserAlbum
import com.hogent.dikkeploaten.database.UserAlbumDao

class UserAlbumRepository private constructor(
    private val userAlbumDao: UserAlbumDao
) {

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

    fun getCollectionUser() = userAlbumDao.getAlbumsUser("collection")
    fun getWantlistUser() = userAlbumDao.getAlbumsUser("wantlist")

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: UserAlbumRepository? = null

        fun getInstance(userAlbumDao: UserAlbumDao) =
            instance ?: synchronized(this) {
                instance ?: UserAlbumRepository(userAlbumDao).also { instance = it }
            }
    }
}