package com.hogent.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkAlbum(
    @Json(name = "id") val albumId: String,
    val title: String,
    val artist: String,
    val thumb: String,
    val description: String,
    val genre: String,
    @Json(name = "released_in") val releaseYear: String,
    @Json(name = "tracklist") val trackList: String,
    val musicians: String
): Parcelable