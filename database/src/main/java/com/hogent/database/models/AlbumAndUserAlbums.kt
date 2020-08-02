package com.hogent.database.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * This class captures the relationship between an [DatabaseAlbum] and a user's [DatabaseUserAlbum], which is
 * used by Room to fetch the related entities.
 */
@Parcelize
data class AlbumAndUserAlbums(
    @Embedded
    val album: DatabaseAlbum,

    @Relation(parentColumn = "id", entityColumn = "album_id")
    val userAlbums: List<@RawValue DatabaseUserAlbum> = emptyList()
) : Parcelable
