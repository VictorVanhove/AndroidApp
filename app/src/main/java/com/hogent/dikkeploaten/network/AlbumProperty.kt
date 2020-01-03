package com.hogent.dikkeploaten.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AlbumProperty(
    val id: String,
    val title: String,
    val artist: String,
    val thumb: String,
    val description: String,
    val genre: String,
    @Json(name = "released_in") val releaseYear: String,
    @Json(name = "tracklist") val trackList: String,
    val musicians: String,
    val images: List<String>
): Parcelable