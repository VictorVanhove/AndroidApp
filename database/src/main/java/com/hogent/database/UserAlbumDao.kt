package com.hogent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.hogent.database.AlbumAndUserAlbums

@Dao
interface UserAlbumDao {

    @Query("SELECT * FROM user_albums")
    fun getAllUserAlbums(): LiveData<List<com.hogent.database.UserAlbum>>

    @Query("SELECT EXISTS(SELECT 1 FROM user_albums WHERE album_id = :albumId LIMIT 1)")
    fun isInCollection(albumId: String): LiveData<Boolean>

    /**
     * This query will tell Room to query both the [DatabaseAlbum] and [UserAlbum] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM album_table WHERE id IN (SELECT DISTINCT(album_id) FROM user_albums WHERE album_type = :type)")
    fun getAlbumsUser(type: String): LiveData<List<com.hogent.database.AlbumAndUserAlbums>>

    @Insert
    suspend fun insertUserAlbum(userAlbum: com.hogent.database.UserAlbum): Long

    @Delete
    suspend fun deleteUserAlbum(userAlbum: com.hogent.database.UserAlbum)

    @Query("SELECT * FROM user_albums WHERE album_id = :albumId")
    suspend fun getUserAlbum(albumId: String): com.hogent.database.UserAlbum
}