package com.hogent.dikkeploaten.models

import android.os.Parcelable
import com.hogent.domain.models.UserAlbum
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.util.*

@Parcelize
data class ViewUserAlbum(
    val albumId: String,
    val albumType: String,
    val albumDate: Calendar,
    val userAlbumId: String
): Parcelable

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