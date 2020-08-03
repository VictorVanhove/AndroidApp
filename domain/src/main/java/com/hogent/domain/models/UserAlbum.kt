package com.hogent.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Model class for userAlbum.
 */
data class UserAlbum(
    val userAlbumId: String,
    val albumId: String,
    val albumType: String,
    val albumDate: Calendar
)