package com.hogent.database.models

import com.hogent.domain.models.Album
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class DatabaseAlbumTest {

    @Test
    fun databaseAlbumToAlbumTest() {

        val databaseAlbum = DatabaseAlbum(
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

        val domainAlbum = databaseAlbum.toAlbum()

        Assert.assertEquals(databaseAlbum.albumId, domainAlbum.albumId)
        Assert.assertEquals(databaseAlbum.title, domainAlbum.title)
        Assert.assertEquals(databaseAlbum.artist, domainAlbum.artist)
        Assert.assertEquals(databaseAlbum.thumb, domainAlbum.thumb)
        Assert.assertEquals(databaseAlbum.description, domainAlbum.description)
        Assert.assertEquals(databaseAlbum.genre, domainAlbum.genre)
        Assert.assertEquals(databaseAlbum.releaseYear, domainAlbum.releaseYear)
        Assert.assertEquals(databaseAlbum.trackList, domainAlbum.trackList)
        Assert.assertEquals(databaseAlbum.musicians, domainAlbum.musicians)
    }

    @Test
    fun albumToDatabaseAlbumTest() {

        val domainAlbum = Album(
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

        val databaseAlbum = domainAlbum.toDatabaseAlbum()

        Assert.assertEquals(domainAlbum.albumId, databaseAlbum.albumId)
        Assert.assertEquals(domainAlbum.title, databaseAlbum.title)
        Assert.assertEquals(domainAlbum.artist, databaseAlbum.artist)
        Assert.assertEquals(domainAlbum.thumb, databaseAlbum.thumb)
        Assert.assertEquals(domainAlbum.description, databaseAlbum.description)
        Assert.assertEquals(domainAlbum.genre, databaseAlbum.genre)
        Assert.assertEquals(domainAlbum.releaseYear, databaseAlbum.releaseYear)
        Assert.assertEquals(domainAlbum.trackList, databaseAlbum.trackList)
        Assert.assertEquals(domainAlbum.musicians, databaseAlbum.musicians)
    }
}