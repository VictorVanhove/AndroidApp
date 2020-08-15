package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.domain.models.AlbumAndUserAlbums
import com.hogent.domain.repositories.UserAlbumRepository

/**
 * Factory for creating a [UserAlbumDetailViewModel] with a constructor that takes a [UserAlbumRepository]
 * and the current userAlbum in [AlbumAndUserAlbums].
 */
class UserAlbumDetailViewModelFactory(
    private val userAlbumRepository: UserAlbumRepository,
    private val userAlbum: AlbumAndUserAlbums
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserAlbumDetailViewModel::class.java)) {
            return UserAlbumDetailViewModel(userAlbumRepository, userAlbum) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
