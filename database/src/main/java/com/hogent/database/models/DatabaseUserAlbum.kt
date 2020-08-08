package com.hogent.database.models

import androidx.room.*
import com.hogent.domain.models.UserAlbum
import java.util.*


/**
 * This class represents the persistent version of an [UserAlbum], used when a user adds a
 * [Album] to their collection or wantlist, with useful metadata.
 * @property userAlbumId the album id of this user album
 *
 * @property albumId the id of the original album
 * @property albumType the location where the user album belongs to, can be collection or wantlist
 * @property albumDate the date when the user album is added
 */
@Entity(
    tableName = "user_albums",
    foreignKeys = [
        ForeignKey(
            entity = DatabaseAlbum::class,
            parentColumns = ["id"],
            childColumns = ["album_id"]
        )
    ],
    indices = [Index("album_id")]
)
internal data class DatabaseUserAlbum(
    @ColumnInfo(name = "album_id") val albumId: String,

    @ColumnInfo(name = "album_type") val albumType: String,

    /**
     * Indicates when the [DatabaseAlbum] was bought. Used as friendly reminder
     */
    @ColumnInfo(name = "album_date") val albumDate: Calendar = Calendar.getInstance()
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var userAlbumId: Long = 0
}

internal fun DatabaseUserAlbum.toUserAlbum(): UserAlbum = UserAlbum(
    albumId = albumId,
    albumType = albumType,
    albumDate = albumDate,
    userAlbumId = userAlbumId.toString()
)
