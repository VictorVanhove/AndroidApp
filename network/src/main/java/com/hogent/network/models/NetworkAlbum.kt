package com.hogent.network.models

import com.hogent.domain.models.Album
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * This class represents the network version of an [Album], which gets called
 * using a HTTP GET-request to an online server.
 * @property albumId the album id of this album
 *
 * @property title title of the album
 * @property artist the album's artist
 * @property thumb the album's thumbnail, cover image
 * @property description description of the album
 * @property genre the genre of the album
 * @property releaseYear the album's release year
 * @property trackList the track list of the album
 * @property musicians the musicians who worked on the album, the band members
 */
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
