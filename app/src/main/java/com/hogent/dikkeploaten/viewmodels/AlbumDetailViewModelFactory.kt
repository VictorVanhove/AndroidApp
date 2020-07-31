package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.database.DatabaseAlbum
import com.hogent.dikkeploaten.repositories.UserAlbumRepository

/**
 * Simple ViewModel factory that provides the DatabaseAlbum and context to the ViewModel.
 */
class AlbumDetailViewModelFactory(
    private val userAlbumRepository: UserAlbumRepository,
    private val album: com.hogent.database.DatabaseAlbum
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
            return AlbumDetailViewModel(userAlbumRepository, album) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
