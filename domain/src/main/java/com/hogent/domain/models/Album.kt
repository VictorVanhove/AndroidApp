package com.hogent.domain.models

/**
 * Model class for album.
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