package com.hogent.database

import com.hogent.database.dao.AlbumDao
import com.hogent.database.dao.UserAlbumDao
import com.hogent.database.models.*
import com.hogent.domain.models.Album
import com.hogent.domain.sources.DatabaseSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

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

    private val listOfDatabaseUserAlbums = listOf(
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

    private val listOfAlbumAndUserAlbums = listOf(
        DatabaseAlbumAndUserAlbums(
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
            ),
            listOf(
                DatabaseUserAlbum(
                    "123",
                    "collection",
                    Calendar.getInstance()
                ),
                DatabaseUserAlbum(
                    "123",
                    "wantlist",
                    Calendar.getInstance()
                )
            )
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

            val result = source.getAlbumList()

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
    fun createUserAlbumDaoCallTest() {

        val dbUserAlbumToCreate = listOfAlbums.map { it.toDatabaseAlbum().albumId }

        runBlocking {

            source.createUserAlbum(dbUserAlbumToCreate[0], "collection")
        }

        coVerify(exactly = 1) {
            userAlbumDao.insertUserAlbum(
                DatabaseUserAlbum(
                    dbUserAlbumToCreate[0],
                    "collection"
                )
            )
        }
    }

    @Test
    fun removeUserAlbumDaoCallTest() {

        val dbUserAlbumToRemove = listOfDatabaseUserAlbums.map { it.toUserAlbum() }

        runBlocking {

            source.removeUserAlbum(dbUserAlbumToRemove[0])
        }

        coVerify(exactly = 1) {
            userAlbumDao.deleteUserAlbum(dbUserAlbumToRemove[0].albumId)
        }
    }

    @Test
    fun getUserAlbumDaoCallTest() {

        coEvery { userAlbumDao.getUserAlbum(any()) } returns DatabaseUserAlbum(
            "123",
            "title",
            Calendar.getInstance()
        )

        runBlocking {

            source.getUserAlbum("id123")
        }

        coVerify(exactly = 1) { userAlbumDao.getUserAlbum("id123") }
    }

    @Test
    fun isInCollectionDaoCallTest() {

        coEvery { userAlbumDao.isInCollection(any()) } returns true

        runBlocking {

            source.isInCollection("id123")
        }

        coVerify(exactly = 1) { userAlbumDao.isInCollection("id123") }
    }

    @Test
    fun getCollectionUserDaoCallTest() {

        coEvery { userAlbumDao.getAlbumsUser("collection") } returns listOfAlbumAndUserAlbums

        runBlocking {

            source.getCollectionUser()
        }

        coVerify(exactly = 1) {
            userAlbumDao.getAlbumsUser("collection")
        }
    }

    @Test
    fun getWantlistUserDaoCallTest() {

        coEvery { userAlbumDao.getAlbumsUser("wantlist") } returns listOfAlbumAndUserAlbums

        runBlocking {

            source.getWantlistUser()
        }

        coVerify(exactly = 1) {
            userAlbumDao.getAlbumsUser("wantlist")
        }
    }
}
