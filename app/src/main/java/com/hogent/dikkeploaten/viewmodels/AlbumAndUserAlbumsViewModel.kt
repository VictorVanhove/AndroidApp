package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.hogent.dikkeploaten.database.AlbumAndUserAlbums
import com.hogent.dikkeploaten.database.DatabaseAlbum
import java.text.SimpleDateFormat
import java.util.*

class AlbumAndUserAlbumsViewModel(albums: AlbumAndUserAlbums) {

    private val album = checkNotNull(albums.album)
    private val userAlbums = albums.userAlbums[0]

    val albumThumb
        get() = album.thumb
    val albumTitle
        get() = album.title
    val albumArtist
        get() = album.artist
    val albumDateString: String = dateFormat.format(userAlbums.albumDate.time)
    val albumId
        get() = album.albumId

    companion object {
        private val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
    }

}