package com.hogent.database.dao

import androidx.room.*
import com.hogent.database.models.DatabaseAlbum

/**
 * The Data Access Object for the [DatabaseAlbum] class.
 */
@Dao
internal interface AlbumDao {

    @Insert
    suspend fun insertAlbum(album: DatabaseAlbum)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAlbums(albums: List<DatabaseAlbum>)

    @Update
    suspend fun updateAlbum(album: DatabaseAlbum)

    @Query("DELETE FROM album_table WHERE id = :albumId")
    suspend fun deleteAlbum(albumId: String)

    @Query("SELECT * FROM album_table")
    suspend fun getAlbumList(): List<DatabaseAlbum>

    @Query("SELECT * FROM album_table WHERE id = :albumId")
    suspend fun getAlbumWithId(albumId: String): DatabaseAlbum
}