package com.hogent.domain.repositories

import com.hogent.domain.models.UserAlbum
import com.hogent.domain.sources.DatabaseSource

/**
 * The Repository module for the [UserAlbum] class, handles data operations.
 */
class UserAlbumRepository private constructor(
    private val databaseDataSource: DatabaseSource
) {

    suspend fun createUserAlbum(albumId: String, albumType: String) {
        databaseDataSource.createUserAlbum(albumId, albumType)
    }

    suspend fun removeUserAlbum(userAlbum: UserAlbum) {
        databaseDataSource.removeUserAlbum(userAlbum)
    }

    suspend fun getUserAlbum(albumId: String): UserAlbum =
        databaseDataSource.getUserAlbum(albumId)

    suspend fun isInCollection(albumId: String) =
        databaseDataSource.isInCollection(albumId)

    suspend fun getCollectionUser() = databaseDataSource.getCollectionUser()

    suspend fun getWantlistUser() = databaseDataSource.getWantlistUser()

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: UserAlbumRepository? = null

        fun getInstance(databaseDataSource: DatabaseSource) =
            instance
                ?: synchronized(this) {
                    instance
                        ?: UserAlbumRepository(
                            databaseDataSource
                        )
                            .also { instance = it }
                }
    }
}