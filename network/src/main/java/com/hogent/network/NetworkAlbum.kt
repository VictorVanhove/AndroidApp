package com.hogent.network

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class NetworkAlbum(
    @Json(name = "id") val albumId: String,
    @Json(name = "title")val title: String,
    @Json(name = "artist")val artist: String,
    @Json(name = "thumb")val thumb: String,
    @Json(name = "description")val description: String,
    @Json(name = "genre")val genre: String,
    @Json(name = "released_in") val releaseYear: String,
    @Json(name = "tracklist") val trackList: String,
    @Json(name = "musicians")val musicians: String
): Parcelable