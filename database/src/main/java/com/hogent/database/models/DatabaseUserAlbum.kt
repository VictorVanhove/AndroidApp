package com.hogent.database.models

import androidx.room.*
import com.hogent.domain.models.UserAlbum
import java.util.*

/**
 * [DatabaseUserAlbum] gets used when a user adds a [DatabaseAlbum] to their collection or wantlist, with useful metadata.
 * Property [albumType] is used to determine the location of the album, whereas [albumDate] is used as a notification when the album is added.
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
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
