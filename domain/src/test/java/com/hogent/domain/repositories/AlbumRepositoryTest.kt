package com.hogent.domain.repositories

import com.hogent.domain.sources.DatabaseSource
import com.hogent.domain.sources.NetworkSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AlbumRepositoryTest {

    @RelaxedMockK
    private lateinit var dbSource: DatabaseSource

    @RelaxedMockK
    private lateinit var networkSource: NetworkSource

    private lateinit var albumRepository: AlbumRepository

    @Before
    fun setUp() {

        MockKAnnotations.init(this)

        albumRepository = AlbumRepository(dbSource, networkSource)
    }

    @Test
    fun getAllAlbumsResultSuccess() {

        runBlocking {

            albumRepository.getAllAlbums()
        }

        coVerify(exactly = 1) {
            dbSource.getAlbumList()
        }
    }

    @Test
    fun updateAlbums() {
        coEvery { networkSource.getAlbumList() } returns listOf()

        runBlocking {

            albumRepository.updateAlbums()
        }

        coVerify(exactly = 1) { networkSource.getAlbumList() }
        coVerify(exactly = 1) { dbSource.insertAllAlbums(any()) }
    }
}