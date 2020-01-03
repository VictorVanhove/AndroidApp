package com.hogent.dikkeploaten.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hogent.dikkeploaten.network.AlbumProperty

/**
 * Simple ViewModel factory that provides the MarsProperty and context to the ViewModel.
 */
class AlbumDetailViewModelFactory(
    private val albumProperty: AlbumProperty,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlbumDetailViewModel::class.java)) {
            return AlbumDetailViewModel(albumProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}