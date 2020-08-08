package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.domain.repositories.UserAlbumRepository

/**
 * Factory for creating a [UserAlbumListViewModel] with a constructor that takes a
 * [UserAlbumRepository].
 */
class UserAlbumListViewModelFactory(
    private val userAlbumRepository: UserAlbumRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserAlbumListViewModel(userAlbumRepository) as T
    }
}