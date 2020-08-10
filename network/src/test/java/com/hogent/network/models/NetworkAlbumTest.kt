package com.hogent.network.models

import org.junit.Assert
import org.junit.Test

class NetworkAlbumTest {

    @Test
    fun toDomainAlbumTest() {

        val networkAlbum = NetworkAlbum(
            "123",
            "TITLE",
            "John Lennon",
            "https://www.google.com/image.jpg",
            "A titleless album",
            "Rock",
            "1965",
            "tracks",
            "Lennon, Starr"
        )

        val domainAlbum = networkAlbum.toAlbum()

        Assert.assertEquals(networkAlbum.albumId, domainAlbum.albumId)
        Assert.assertEquals(networkAlbum.title, domainAlbum.title)
        Assert.assertEquals(networkAlbum.artist, domainAlbum.artist)
        Assert.assertEquals(networkAlbum.thumb, domainAlbum.thumb)
        Assert.assertEquals(networkAlbum.description, domainAlbum.description)
        Assert.assertEquals(networkAlbum.genre, domainAlbum.genre)
        Assert.assertEquals(networkAlbum.releaseYear, domainAlbum.releaseYear)
        Assert.assertEquals(networkAlbum.trackList, domainAlbum.trackList)
        Assert.assertEquals(networkAlbum.musicians, domainAlbum.musicians)
    }
}
