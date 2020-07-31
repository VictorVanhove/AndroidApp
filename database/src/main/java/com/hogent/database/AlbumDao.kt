package com.hogent.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AlbumDao {

    @Insert
    fun insertAlbum(album: DatabaseAlbum)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlbums(albums: List<DatabaseAlbum>)

    @Update
    fun updateAlbum(album: DatabaseAlbum)

    @Query("DELETE FROM album_table WHERE id = :albumId")
    fun deleteAlbum(albumId: String)

    @Query("SELECT * FROM album_table")
    fun getAlbumList(): LiveData<List<DatabaseAlbum>>

    @Query("SELECT * FROM album_table WHERE id = :albumId")
    fun getAlbumWithId(albumId: String): LiveData<DatabaseAlbum>
}