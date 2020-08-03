package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.domain.models.Album
import com.hogent.domain.repositories.UserAlbumRepository

/**
 * Simple ViewModel factory that provides the DatabaseAlbum and context to the ViewModel.
 */
class AlbumDetailViewModelFactory(
    private val userAlbumRepository: com.hogent.domain.repositories.UserAlbumRepository,
    private val album: com.hogent.domain.models.Album
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
            return AlbumDetailViewModel(userAlbumRepository, album) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
