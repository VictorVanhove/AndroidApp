package com.hogent.dikkeploaten.repositories

import androidx.lifecycle.LiveData
import com.hogent.dikkeploaten.database.AlbumDao
import com.hogent.dikkeploaten.database.DatabaseAlbum

class AlbumRepository private constructor(private val albumDao: AlbumDao) {

    fun getAlbums(): LiveData<List<DatabaseAlbum>> {
        return albumDao.getAlbumList()
    }

    fun getAlbumWithId(id: String): LiveData<DatabaseAlbum> {
        return albumDao.getAlbumWithId(id)
    }

    /* Adds the online albums to the local database */
    fun saveAlbumsToDatabase(albums: List<DatabaseAlbum>) {
        albumDao.insertAllAlbums(albums)
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
