package com.hogent.dikkeploaten.models

import android.os.Parcelable
import com.hogent.domain.models.Album
import kotlinx.android.parcel.Parcelize

/**
 * This class represents a view version of an [Album], loaded from the network,
 * is made parcelable and thus interacts with the UI and the user.
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
) : Parcelable

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
