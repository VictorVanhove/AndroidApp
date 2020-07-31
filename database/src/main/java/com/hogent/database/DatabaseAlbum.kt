package com.hogent.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "album_table")
@Parcelize
data class DatabaseAlbum (
    @PrimaryKey @ColumnInfo(name = "id") val albumId: String,
    val title: String,
    val artist: String,
    val thumb: String,
    val description: String,
    val genre: String,
    @ColumnInfo(name = "released_in") val releaseYear: String,
    @ColumnInfo(name = "tracklist") val trackList: String,
    val musicians: String
): Parcelable