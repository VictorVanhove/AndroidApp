package com.hogent.domain.models

/**
 * This class represents the model class of [Album].
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
data class Album(
    var albumId: String,
    var title: String,
    var artist: String,
    var thumb: String,
    var description: String,
    var genre: String,
    var releaseYear: String,
    var trackList: String,
    var musicians: String
)