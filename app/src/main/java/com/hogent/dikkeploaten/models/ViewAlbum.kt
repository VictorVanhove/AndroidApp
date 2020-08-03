package com.hogent.dikkeploaten.models

import android.os.Parcelable
import com.hogent.domain.models.Album
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewAlbum(
    val albumId: String,
    val title: String,
    val artist: String,
    val thumb: String,
    val description: String,
    val genre: String,
    val releaseYear: String,
    val trackList: String,
    val musicians: String
): Parcelable

internal fun ViewAlbum.toAlbum() = Album(
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

internal fun Album.toViewAlbum() = ViewAlbum(
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