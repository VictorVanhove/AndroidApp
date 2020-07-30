package com.hogent.dikkeploaten.repositories

import androidx.lifecycle.LiveData
import com.hogent.dikkeploaten.database.AlbumDao
import com.hogent.dikkeploaten.database.DatabaseAlbum
import com.hogent.dikkeploaten.network.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AlbumRepository private constructor(private val albumDao: AlbumDao) {

    val albums: LiveData<List<DatabaseAlbum>> = albumDao.getAlbumList()

    fun getAlbumWithId(id: String): LiveData<DatabaseAlbum> {
        return albumDao.getAlbumWithId(id)
    }

    suspend fun updateAlbums() {
        withContext(Dispatchers.IO) {
            val listResult = try {
                // Make network request using a blocking call
                Api.retrofitService.getAlbumList()
            } catch (cause: Throwable) {
                // If the network throws an exception, inform the caller
                throw Error("Unable to refresh albums", cause)
            }

            albumDao.insertAllAlbums(listResult)
        }
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AlbumRepository? = null

        fun getInstance(albumDao: AlbumDao) =
            instance ?: synchronized(this) {
                instance ?: AlbumRepository(albumDao).also { instance = it }
            }
    }

}
