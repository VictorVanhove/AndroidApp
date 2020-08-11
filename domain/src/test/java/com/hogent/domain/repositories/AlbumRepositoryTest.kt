package com.hogent.domain.repositories

import com.hogent.domain.models.Album
import com.hogent.domain.sources.DatabaseSource
import com.hogent.domain.sources.NetworkSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class AlbumRepositoryTest {

    @RelaxedMockK
    private lateinit var dbSource: DatabaseSource

    @RelaxedMockK
    private lateinit var networkSource: NetworkSource

    private lateinit var albumRepository: AlbumRepository

    @Before
    fun setUp() {

        MockKAnnotations.init(this)

        albumRepository = AlbumRepository.getInstance(dbSource, networkSource)
    }

    @Test
    fun getAllAlbumsResultSuccess() {

        runBlocking {

            albumRepository.getAllAlbums()
        }

        coVerify { dbSource.getAlbumList() }
    }

    @Test
    fun updateAlbums() {
    }
}