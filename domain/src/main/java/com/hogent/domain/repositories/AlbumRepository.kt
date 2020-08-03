package com.hogent.domain.repositories

import com.hogent.domain.models.Album
import com.hogent.domain.sources.DatabaseSource
import com.hogent.domain.sources.NetworkSource

class AlbumRepository private constructor(
    private val databaseDataSource: DatabaseSource,
    private val networkDataSource: NetworkSource
) {

    suspend fun getAllAlbums(): List<Album> {
        return databaseDataSource.getAlbumList()
    }

    suspend fun updateAlbums() {
        val listResult = try {
            // Make network request using a blocking call
            networkDataSource.getAlbumList()
        } catch (cause: Throwable) {
            // If the network throws an exception, inform the caller
            throw Error("Unable to refresh albums", cause)
        }

        databaseDataSource.insertAllAlbums(listResult)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AlbumRepository? = null

        fun getInstance(dbDataSource: DatabaseSource, networkDataSource: NetworkSource) =
            instance ?: synchronized(this) {
                instance
                    ?: AlbumRepository(
                        dbDataSource,
                        networkDataSource
                    )
                        .also { instance = it }
            }
    }

}
