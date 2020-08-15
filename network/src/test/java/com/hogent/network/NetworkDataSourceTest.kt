package com.hogent.network

import com.hogent.domain.models.Album
import com.hogent.domain.sources.NetworkSource
import com.hogent.network.models.NetworkAlbum
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class NetworkDataSourceTest {

    @RelaxedMockK
    private lateinit var apiService: ApiService

    private lateinit var source: NetworkSource

    private val listOfNetworkAlbums = listOf(
        NetworkAlbum(
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
        NetworkAlbum(
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

        source = NetworkDataSource(apiService)

        coEvery { apiService.getAlbumList() } returns listOfNetworkAlbums
    }

    @Test
    fun getAlbumListApiCallSuccessTest() {

        runBlocking {

            source.getAlbumList()
        }

        coVerify(exactly = 1) { apiService.getAlbumList() }
    }

    @Test
    fun getAlbumListResultSuccessTest() {

        runBlocking {

            val result = source.getAlbumList()

            // Check correct conversion (lists are the variables pictured above)
            assertEquals(listOfAlbums.size, result.size)
            assertEquals(listOfAlbums[0].albumId, result[0].albumId)
            assertEquals(listOfAlbums, result)
        }
    }
}
