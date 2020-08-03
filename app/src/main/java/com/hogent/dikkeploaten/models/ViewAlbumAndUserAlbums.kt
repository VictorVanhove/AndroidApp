package com.hogent.dikkeploaten.models

import android.os.Parcelable
import com.hogent.domain.models.AlbumAndUserAlbums
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ViewAlbumAndUserAlbums(
    val album: ViewAlbum,
    val userAlbums: List<ViewUserAlbum> = emptyList()
): Parcelable

internal fun ViewAlbumAndUserAlbums.toAlbumAndUserAlbums() = AlbumAndUserAlbums(
    album = album.toAlbum(),
    userAlbums = userAlbums.map { it.toUserAlbum() }
)

internal fun AlbumAndUserAlbums.toViewAlbumAndUserAlbums() = ViewAlbumAndUserAlbums(
    this.album.toViewAlbum(),
    this.userAlbums.map { it.toViewUserAlbum() }
)
