package com.hogent.database.dao

import androidx.room.*
import com.hogent.database.models.AlbumAndUserAlbums
import com.hogent.database.models.UserAlbum

@Dao
internal interface UserAlbumDao {

    @Query("SELECT * FROM user_albums")
    suspend fun getAllUserAlbums(): List<UserAlbum>

    @Query("SELECT EXISTS(SELECT 1 FROM user_albums WHERE album_id = :albumId LIMIT 1)")
    suspend fun isInCollection(albumId: String): Boolean

    /**
     * This query will tell Room to query both the [DatabaseAlbum] and [UserAlbum] tables and handle
     * the object mapping.
     */
    @Transaction
    @Query("SELECT * FROM album_table WHERE id IN (SELECT DISTINCT(album_id) FROM user_albums WHERE album_type = :type)")
    suspend fun getAlbumsUser(type: String): List<AlbumAndUserAlbums>

    @Insert
    suspend fun insertUserAlbum(userAlbum: UserAlbum): Long

    @Delete
    suspend fun deleteUserAlbum(userAlbum: UserAlbum)

    @Query("SELECT * FROM user_albums WHERE album_id = :albumId")
    suspend fun getUserAlbum(albumId: String): UserAlbum
}