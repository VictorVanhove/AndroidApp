package com.hogent.database.models

import org.junit.Assert
import org.junit.Test
import java.util.*

class DatabaseAlbumAndUserAlbumsTest {

    @Test
    fun toAlbumAndUserAlbumsTest() {
        val databaseAlbumAndUserAlbum = DatabaseAlbumAndUserAlbums(
            DatabaseAlbum(
                "123",
                "TITLE",
                "John Lennon",
                "https://www.google.com/image.jpg",
                "A titleless album",
                "Rock",
                "1965",
                "tracks",
                "Lennon, Starr"
            ), listOf(
                DatabaseUserAlbum(
                    "123",
                    "collection",
                    Calendar.getInstance()
                ),
                DatabaseUserAlbum(
                    "456",
                    "wantlist",
                    Calendar.getInstance()
                )

            )
        )

        val domainAlbumAndUserAlbum = databaseAlbumAndUserAlbum.toAlbumAndUserAlbums()

        Assert.assertEquals(
            databaseAlbumAndUserAlbum.album.albumId,
            domainAlbumAndUserAlbum.album.albumId
        )
        Assert.assertEquals(
            databaseAlbumAndUserAlbum.album.title,
            domainAlbumAndUserAlbum.album.title
        )
        Assert.assertEquals(
            databaseAlbumAndUserAlbum.album.artist,
            domainAlbumAndUserAlbum.album.artist
        )
        Assert.assertEquals(
            databaseAlbumAndUserAlbum.album.thumb,
            domainAlbumAndUserAlbum.album.thumb
        )
        Assert.assertEquals(
            databaseAlbumAndUserAlbum.album.description,
            domainAlbumAndUserAlbum.album.description
        )
        Assert.assertEquals(
            databaseAlbumAndUserAlbum.album.genre,
            domainAlbumAndUserAlbum.album.genre
        )
        Assert.assertEquals(
            databaseAlbumAndUserAlbum.album.releaseYear,
            domainAlbumAndUserAlbum.album.releaseYear
        )
        Assert.assertEquals(
            databaseAlbumAndUserAlbum.album.trackList,
            domainAlbumAndUserAlbum.album.trackList
        )
        Assert.assertEquals(
            databaseAlbumAndUserAlbum.album.musicians,
            domainAlbumAndUserAlbum.album.musicians
        )
    }
}