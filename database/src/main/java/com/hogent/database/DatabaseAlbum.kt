package com.hogent.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "album_table")
@Parcelize
data class DatabaseAlbum (
    @PrimaryKey @ColumnInfo(name = "id") @Json(name = "id") val albumId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "thumb") val thumb: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "genre") val genre: String,
    @Json(name = "released_in") @ColumnInfo(name = "released_in") val releaseYear: String,
    @Json(name = "tracklist") @ColumnInfo(name = "tracklist") val trackList: String,
    @ColumnInfo(name = "musicians") val musicians: String
//    @ColumnInfo(name = "images")
//    val images: List<String> = listOf()
) : Parcelable