package com.hogent.dikkeploaten.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.dikkeploaten.database.AlbumAndUserAlbums
import com.hogent.dikkeploaten.repositories.UserAlbumRepository

/**
 * Simple ViewModel factory that provides the AlbumAndUserAlbums and context to the ViewModel.
 */
class UserAlbumDetailViewModelFactory
 (
    private val userAlbumRepository: UserAlbumRepository,
    private val album: AlbumAndUserAlbums) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserAlbumDetailViewModel::class.java)) {
            return UserAlbumDetailViewModel(userAlbumRepository, album) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
    }
