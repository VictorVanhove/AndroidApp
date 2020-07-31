package com.hogent.database

import androidx.room.Embedded
import androidx.room.Relation

/**
 * This class captures the relationship between an [Album] and a user's [UserAlbum], which is
 * used by Room to fetch the related entities.
 */
data class AlbumAndUserAlbums(
    @Embedded
    val album: DatabaseAlbum,

    @Relation(parentColumn = "id", entityColumn = "album_id")
    val userAlbums: List<UserAlbum> = emptyList()
)
