package com.hogent.domain.models

data class AlbumAndUserAlbums(
    val album: Album,
    val userAlbums: List<UserAlbum> = emptyList()
)