package com.hogent.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hogent.domain.models.Album

/**
 * This class represents the persistent version of an [Album], loaded from the network.
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
@Entity(tableName = "album_table")
internal data class DatabaseAlbum(
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