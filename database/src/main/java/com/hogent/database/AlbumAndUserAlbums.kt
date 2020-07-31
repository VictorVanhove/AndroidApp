package com.hogent.database

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import com.hogent.dikkeploaten.database.DatabaseAlbum
import com.hogent.dikkeploaten.database.UserAlbum
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * This class captures the relationship between an [Album] and a user's [UserAlbum], which is
 * used by Room to fetch the related entities.
 */
@Parcelize
data class AlbumAndUserAlbums(
    @Embedded
    val album: DatabaseAlbum,

    @Relation(parentColumn = "id", entityColumn = "album_id")
    val userAlbums: @RawValue List<UserAlbum> = emptyList()
) : Parcelable
