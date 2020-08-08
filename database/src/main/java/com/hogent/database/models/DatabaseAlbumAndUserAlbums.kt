package com.hogent.database.models

import androidx.room.Embedded
import androidx.room.Relation
import com.hogent.domain.models.AlbumAndUserAlbums
import kotlinx.android.parcel.RawValue

/**
 * This class captures the relationship between an [DatabaseAlbum] and a user's [DatabaseUserAlbum],
 * which is used by Room to fetch the related entities.
 *
 * @property album the related album
 * @property userAlbums the related list with their user albums
 */
internal data class DatabaseAlbumAndUserAlbums(
    @Embedded
    val album: DatabaseAlbum,

    @Relation(parentColumn = "id", entityColumn = "album_id")
    val userAlbums: List<@RawValue DatabaseUserAlbum> = emptyList()
)

internal fun DatabaseAlbumAndUserAlbums.toAlbumAndUserAlbums(): AlbumAndUserAlbums =
    AlbumAndUserAlbums(album = album.toAlbum(), userAlbums = userAlbums.map { it.toUserAlbum() })