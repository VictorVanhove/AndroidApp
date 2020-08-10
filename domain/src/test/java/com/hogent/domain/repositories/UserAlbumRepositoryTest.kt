package com.hogent.domain.repositories

import com.hogent.domain.sources.DatabaseSource
import com.hogent.domain.sources.NetworkSource
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class UserAlbumRepositoryTest {

    @RelaxedMockK
    private lateinit var dbSource: DatabaseSource

    @RelaxedMockK
    private lateinit var networkSource: NetworkSource

    private lateinit var userAlbumRepository: UserAlbumRepository

    @Before
    fun setUp() {

        MockKAnnotations.init(this)

        userAlbumRepository = UserAlbumRepository.getInstance(dbSource)
    }
}