package com.hogent.dikkeploaten.repositories

import androidx.lifecycle.LiveData
import com.hogent.database.DatabaseDataSource
import com.hogent.database.models.DatabaseAlbum
import com.hogent.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository private constructor(
    private val databaseDataSource: DatabaseDataSource,
    private val networkDataSource: NetworkDataSource
) {

    suspend fun getAllAlbums(): List<DatabaseAlbum> {
        return databaseDataSource.getAlbumList()
    }

    suspend fun getAlbumWithId(id: String): DatabaseAlbum {
        return databaseDataSource.getAlbumWithId(id)
    }

    suspend fun updateAlbums() {
        withContext(Dispatchers.IO) {
            val listResult = try {
                // Make network request using a blocking call
                networkDataSource.getAlbumList()
            } catch (cause: Throwable) {
                // If the network throws an exception, inform the caller
                throw Error("Unable to refresh albums", cause)
            }

            databaseDataSource.insertAllAlbums(listResult.map {
                DatabaseAlbum(
                    it.albumId,
                    it.title,
                    it.artist,
                    it.thumb,
                    it.description,
                    it.genre,
                    it.releaseYear,
                    it.trackList,
                    it.musicians
                )
            })
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AlbumRepository? = null

        fun getInstance(dbDataSource: DatabaseDataSource, networkDataSource: NetworkDataSource) =
            instance ?: synchronized(this) {
                instance ?: AlbumRepository(dbDataSource, networkDataSource).also { instance = it }
            }
    }

}
