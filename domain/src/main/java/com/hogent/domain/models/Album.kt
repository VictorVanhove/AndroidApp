package com.hogent.domain.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Model class for album.
 */
@Parcelize
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
): Parcelable