package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.dikkeploaten.repositories.UserAlbumRepository

/**
 * Factory for creating a [GardenPlantingListViewModel] with a constructor that takes a
 * [GardenPlantingRepository].
 */
class UserAlbumListViewModelFactory(
    private val repository: UserAlbumRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserAlbumListViewModel(repository) as T
    }
}