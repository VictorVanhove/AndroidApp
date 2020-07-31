package com.hogent.dikkeploaten.repositories

import androidx.lifecycle.LiveData
import com.hogent.database.AlbumDao
import com.hogent.database.DatabaseAlbum
import com.hogent.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository private constructor(private val albumDao: com.hogent.database.AlbumDao) {

    val albums: LiveData<List<com.hogent.database.DatabaseAlbum>> = albumDao.getAlbumList()

    fun getAlbumWithId(id: String): LiveData<com.hogent.database.DatabaseAlbum> {
        return albumDao.getAlbumWithId(id)
    }

    suspend fun updateAlbums() {
        withContext(Dispatchers.IO) {
            val listResult = try {
                // Make network request using a blocking call
                com.hogent.network.Api.retrofitService.getAlbumList()
            } catch (cause: Throwable) {
                // If the network throws an exception, inform the caller
                throw Error("Unable to refresh albums", cause)
            }

            albumDao.insertAllAlbums(listResult.map { DatabaseAlbum(it.albumId, it.title, it.artist, it.thumb, it.description, it.genre, it.releaseYear, it.trackList, it.musicians) })
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AlbumRepository? = null

        fun getInstance(albumDao: com.hogent.database.AlbumDao) =
            instance ?: synchronized(this) {
                instance ?: AlbumRepository(albumDao).also { instance = it }
            }
    }

}
