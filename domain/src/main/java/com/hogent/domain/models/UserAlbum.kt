package com.hogent.domain.models

import java.util.*

/**
 * This class represents the model class of an [UserAlbum], used when a user adds a
 * [Album] to their collection or wantlist, with useful metadata.
 * @property userAlbumId the album id of this user album
 *
 * @property albumId the id of the original album
 * @property albumType the location where the user album belongs to, can be collection or wantlist
 * @property albumDate the date when the user album is added
 */
data class UserAlbum(
    val userAlbumId: String,
    val albumId: String,
    val albumType: String,
    val albumDate: Calendar
)