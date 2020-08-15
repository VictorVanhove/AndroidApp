package com.hogent.domain.repositories

import com.hogent.domain.models.UserAlbum
import com.hogent.domain.sources.DatabaseSource
import com.hogent.domain.sources.NetworkSource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import java.util.*

class UserAlbumRepositoryTest {

    @RelaxedMockK
    private lateinit var dbSource: DatabaseSource

    @RelaxedMockK
    private lateinit var networkSource: NetworkSource

    private lateinit var userAlbumRepository: UserAlbumRepository

    @Before
    fun setUp() {

        MockKAnnotations.init(this)

        userAlbumRepository = UserAlbumRepository(dbSource)
    }

    @Test
    fun getUserAlbumResultSuccess() {

        coEvery { dbSource.getUserAlbum(any()) } returns UserAlbum(
            "123",
            "456",
            "collection",
            Calendar.getInstance()
        )

        runBlocking {

            userAlbumRepository.getUserAlbum("0")
        }

        coVerify(exactly = 1) {
            dbSource.getUserAlbum(any())
        }
    }
}
