package com.hogent.database.models

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(
    tableName = "user_albums",
    foreignKeys = [
        ForeignKey(entity = DatabaseAlbum::class, parentColumns = ["id"], childColumns = ["album_id"])
    ],
    indices = [Index("album_id")]
)
data class DatabaseUserAlbum(
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
