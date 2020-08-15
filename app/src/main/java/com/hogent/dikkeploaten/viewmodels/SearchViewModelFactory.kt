package com.hogent.dikkeploaten.viewmodels

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.hogent.domain.repositories.AlbumRepository

/**
 * Factory for creating a [SearchViewModel] with a constructor that takes a [AlbumRepository].
 */
class SearchViewModelFactory(
    private val albumRepository: AlbumRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return SearchViewModel(albumRepository) as T
    }
}
