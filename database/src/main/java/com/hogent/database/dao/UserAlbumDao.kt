package com.hogent.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hogent.database.models.AlbumAndUserAlbums
import com.hogent.database.models.UserAlbum

@Dao
internal interface UserAlbumDao {

    @Query("SELECT * FROM user_albums")
    fun getAllUserAlbums(): LiveData<List<UserAlbum>>

    @Query("SELECT EXISTS(SELECT 1 FROM user_albums WHERE album_id = :albumId LIMIT 1)")
    fun isInCollection(albumId: String): LiveData<Boolean>

    /**
     * This query will tell Room to query both the [DatabaseAlbum] and [UserAlbum] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM album_table WHERE id IN (SELECT DISTINCT(album_id) FROM user_albums WHERE album_type = :type)")
    fun getAlbumsUser(type: String): LiveData<List<AlbumAndUserAlbums>>

    @Insert
    suspend fun insertUserAlbum(userAlbum: UserAlbum): Long

    @Delete
    suspend fun deleteUserAlbum(userAlbum: UserAlbum)

    @Query("SELECT * FROM user_albums WHERE album_id = :albumId")
    suspend fun getUserAlbum(albumId: String): UserAlbum
}