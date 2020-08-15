package com.hogent.dikkeploaten.viewmodels

import com.hogent.dikkeploaten.models.ViewAlbumAndUserAlbums
import java.text.SimpleDateFormat
import java.util.*

/**
 * The ViewModel used in [UserAlbumAdapter] for binding the property to the view.
 */
internal class AlbumAndUserAlbumsViewModel(albums: ViewAlbumAndUserAlbums) {

    private val album = checkNotNull(albums.album)
    private val userAlbums = albums.userAlbums[0]

    val albumThumb
        get() = album.thumb
    val albumTitle
        get() = album.title
    val albumArtist
        get() = album.artist
    val albumDateString: String = dateFormat.format(userAlbums.albumDate.time)

    companion object {
        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    }
}
