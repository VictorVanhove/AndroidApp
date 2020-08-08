package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.domain.models.Album
import com.hogent.domain.repositories.UserAlbumRepository

/**
 * Factory for creating a [AlbumDetailViewModel] with a constructor that takes a [UserAlbumRepository]
 * and the current [Album].
 */
class AlbumDetailViewModelFactory(
    private val userAlbumRepository: UserAlbumRepository,
    private val album: Album
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
            return AlbumDetailViewModel(userAlbumRepository, album) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
