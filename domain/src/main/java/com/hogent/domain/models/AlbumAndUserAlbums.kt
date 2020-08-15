package com.hogent.domain.models

/**
 * This model class represents the relationship between an [Album] and a user's [UserAlbum],
 * which is used by Room to fetch the related entities.
 *
 * @property album the related album
 * @property userAlbums the related list with their user albums
 */
data class AlbumAndUserAlbums(
    val album: Album,
    val userAlbums: List<UserAlbum> = emptyList()
)
