package com.hogent.dikkeploaten.models

import android.os.Parcelable
import com.hogent.domain.models.AlbumAndUserAlbums
import kotlinx.android.parcel.Parcelize

/**
 * This class captures the relationship between an [ViewAlbum] and a user's [ViewUserAlbum],
 * which is used by Room to fetch the related entities. It interacts with the view, so parcelable
 * is again needed.
 *
 * @property album the related album
 * @property userAlbums the related list with their user albums
 */
@Parcelize
data class ViewAlbumAndUserAlbums(
    val album: ViewAlbum,
    val userAlbums: List<ViewUserAlbum> = emptyList()
) : Parcelable

internal fun ViewAlbumAndUserAlbums.toAlbumAndUserAlbums() = AlbumAndUserAlbums(
    album = album.toAlbum(),
    userAlbums = userAlbums.map { it.toUserAlbum() }
)

internal fun AlbumAndUserAlbums.toViewAlbumAndUserAlbums() = ViewAlbumAndUserAlbums(
    this.album.toViewAlbum(),
    this.userAlbums.map { it.toViewUserAlbum() }
)
