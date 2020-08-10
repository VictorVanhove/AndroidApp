package com.hogent.database

import com.hogent.database.dao.AlbumDao
import com.hogent.database.dao.UserAlbumDao
import com.hogent.database.models.DatabaseAlbum
import com.hogent.database.models.toDatabaseAlbum
import com.hogent.domain.models.Album
import com.hogent.domain.sources.DatabaseSource
import com.hogent.domain.sources.NetworkSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class DatabaseDataSourceTest {

    @RelaxedMockK
    private lateinit var albumDao: AlbumDao

    @RelaxedMockK
    private lateinit var userAlbumDao: UserAlbumDao

    private lateinit var source: DatabaseSource

    private val listOfDatabaseAlbums = listOf(
        DatabaseAlbum(
            "123",
            "title",
            "John Lennon",
            "https://www.google.com/image.jpg",
            "A titleless album",
            "Rock",
            "1965",
            "tracks",
            "Lennon, Starr"
        ),
        DatabaseAlbum(
            "123",
            "Jailhouse rock",
            "Elvis Operator ?:",
            "https://www.google.com/elvis.jpg",
            "A prison song",
            "Rock",
            "1965",
            "tracks",
            "Elvis, Presley"
        )
    )

    private val listOfAlbums = listOf(
        Album(
            "123",
            "title",
            "John Lennon",
            "https://www.google.com/image.jpg",
            "A titleless album",
            "Rock",
            "1965",
            "tracks",
            "Lennon, Starr"
        ),
        Album(
            "123",
            "Jailhouse rock",
            "Elvis Operator ?:",
            "https://www.google.com/elvis.jpg",
            "A prison song",
            "Rock",
            "1965",
            "tracks",
            "Elvis, Presley"
        )
    )

    @Before
    fun setUp() {

        MockKAnnotations.init(this)

        source = DatabaseDataSource(albumDao, userAlbumDao)
    }

    @Test
    fun getAlbumListDaoCallTest() {

        coEvery { albumDao.getAlbumList() } returns listOfDatabaseAlbums

        runBlocking {

            source.getAlbumList()
        }

        coVerify(exactly = 1) { albumDao.getAlbumList() }
    }

    @Test
    fun getAlbumListSuccessResultTest() {

        coEvery { albumDao.getAlbumList() } returns listOfDatabaseAlbums

        runBlocking {

            val result =  source.getAlbumList()

            // Check correct conversion (lists are the variables pictured above)
            assertEquals(listOfAlbums.size, result.size)
            assertEquals(listOfAlbums[0].albumId, result[0].albumId)
            assertEquals(listOfAlbums, result)
        }
    }

    @Test
    fun getAlbumWithIdDaoCallTest() {

        coEvery { albumDao.getAlbumWithId(any()) } returns DatabaseAlbum(
            "123",
            "title",
            "John Lennon",
            "https://www.google.com/image.jpg",
            "A titleless album",
            "Rock",
            "1965",
            "tracks",
            "Lennon, Starr"
        )

        runBlocking {

            source.getAlbumWithId("id123")
        }

        coVerify(exactly = 1) { albumDao.getAlbumWithId("id123") }
    }

    @Test
    fun insertAllAlbumsDaoCallTest() {

        val dbAlbumsToInsert = listOfAlbums.map { it.toDatabaseAlbum() }

        runBlocking {

            source.insertAllAlbums(listOfAlbums)
        }

        coVerify(exactly = 1) { albumDao.insertAllAlbums(dbAlbumsToInsert) }
    }

    @Test
    fun createUserAlbum() {
    }

    @Test
    fun removeUserAlbum() {
    }

    @Test
    fun getUserAlbum() {
    }

    @Test
    fun isInCollection() {
    }

    @Test
    fun getCollectionUser() {
    }

    @Test
    fun getWantlistUser() {
    }
}