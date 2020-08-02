package com.hogent.dikkeploaten.repositories

import com.hogent.database.DatabaseDataSource
import com.hogent.database.models.DatabaseUserAlbum

class UserAlbumRepository private constructor(
    private val databaseDataSource: DatabaseDataSource
) {

    suspend fun createUserAlbum(albumId: String, albumType: String) {
        databaseDataSource.createUserAlbum(albumId, albumType)
    }

    suspend fun removeUserAlbum(userAlbum: DatabaseUserAlbum) {
        databaseDataSource.removeUserAlbum(userAlbum)
    }

    suspend fun getUserAlbum(albumId: String): DatabaseUserAlbum =
        databaseDataSource.getUserAlbum(albumId)

    suspend fun isInCollection(albumId: String) =
        databaseDataSource.isInCollection(albumId)

    suspend fun getCollectionUser() = databaseDataSource.getCollectionUser()

    suspend fun getWantlistUser() = databaseDataSource.getWantlistUser()

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: UserAlbumRepository? = null

        fun getInstance(databaseDataSource: DatabaseDataSource) =
            instance ?: synchronized(this) {
                instance ?: UserAlbumRepository(databaseDataSource).also { instance = it }
            }
    }
}