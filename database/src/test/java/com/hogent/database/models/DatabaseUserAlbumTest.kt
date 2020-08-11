package com.hogent.database.models

import org.junit.Assert
import org.junit.Test
import java.util.*

class DatabaseUserAlbumTest {

    @Test
    fun databaseUserAlbumtoUserAlbumTest() {

        val databaseUserAlbum = DatabaseUserAlbum(
            "123",
            "collection",
            Calendar.getInstance()
        )

        val domainUserAlbum = databaseUserAlbum.toUserAlbum()

        Assert.assertEquals(domainUserAlbum.userAlbumId, "0")
        Assert.assertEquals(domainUserAlbum.albumId, databaseUserAlbum.albumId)
        Assert.assertEquals(domainUserAlbum.albumType, databaseUserAlbum.albumType)
        Assert.assertEquals(domainUserAlbum.albumDate, databaseUserAlbum.albumDate)
    }
}