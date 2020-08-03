package com.hogent.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hogent.domain.models.Album
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "album_table")
internal data class DatabaseAlbum (
    @PrimaryKey @ColumnInfo(name = "id") val albumId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "artist") val artist: String,
    @ColumnInfo(name = "thumb") val thumb: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "genre") val genre: String,
    @ColumnInfo(name = "released_in") val releaseYear: String,
    @ColumnInfo(name = "tracklist") val trackList: String,
    @ColumnInfo(name = "musicians") val musicians: String
)

internal fun DatabaseAlbum.toAlbum(): Album = Album(
    albumId = albumId,
    title = title,
    artist = artist,
    thumb = thumb,
    description = description,
    genre = genre,
    releaseYear = releaseYear,
    trackList = trackList,
    musicians = musicians
)

internal fun Album.toDatabaseAlbum(): DatabaseAlbum = DatabaseAlbum(
    albumId = albumId,
    title = title,
    artist = artist,
    thumb = thumb,
    description = description,
    genre = genre,
    releaseYear = releaseYear,
    trackList = trackList,
    musicians = musicians
)