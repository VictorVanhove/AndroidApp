package com.hogent.network.models

import android.os.Parcelable
import com.hogent.domain.models.Album
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
internal data class NetworkAlbum(
    @Json(name = "id") val albumId: String,
    @Json(name = "title") val title: String,
    @Json(name = "artist") val artist: String,
    @Json(name = "thumb") val thumb: String,
    @Json(name = "description") val description: String,
    @Json(name = "genre") val genre: String,
    @Json(name = "released_in") val releaseYear: String,
    @Json(name = "tracklist") val trackList: String,
    @Json(name = "musicians") val musicians: String
)

internal fun NetworkAlbum.toAlbum(): Album = Album(
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