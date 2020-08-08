package com.hogent.dikkeploaten.models

import android.os.Parcelable
import com.hogent.domain.models.UserAlbum
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * This class represents the view version of an [UserAlbum], used when a user adds an
 * [Album] to their collection or wantlist, with useful metadata.
 * @property userAlbumId the album id of this user album
 *
 * @property albumId the id of the original album
 * @property albumType the location where the user album belongs to, can be collection or wantlist
 * @property albumDate the date when the user album is added
 */
@Parcelize
data class ViewUserAlbum(
    val albumId: String,
    val albumType: String,
    val albumDate: Calendar,
    val userAlbumId: String
) : Parcelable

internal fun ViewUserAlbum.toUserAlbum() = UserAlbum(
    albumId = albumId,
    albumType = albumType,
    albumDate = albumDate,
    userAlbumId = userAlbumId
)

internal fun UserAlbum.toViewUserAlbum() = ViewUserAlbum(
    albumId = albumId,
    albumType = albumType,
    albumDate = albumDate,
    userAlbumId = userAlbumId
)