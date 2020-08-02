package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hogent.database.models.DatabaseAlbum
import com.hogent.dikkeploaten.repositories.UserAlbumRepository
import kotlinx.coroutines.launch

/**
 *  The [ViewModel] associated with the [AlbumDetailFragment], containing information about the selected
 *  [Album].
 */
class AlbumDetailViewModel(
    private val userAlbumRepository: UserAlbumRepository,
    private val album: DatabaseAlbum
)   : ViewModel() {
    private val _selectedProperty = MutableLiveData<DatabaseAlbum>()

    // The external LiveData for the SelectedProperty
    val selectedProperty: LiveData<DatabaseAlbum>
        get() = _selectedProperty

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = album
    }

    val isInCollection = userAlbumRepository.isInCollection(album.albumId)

    fun addAlbumToCollection() {
        viewModelScope.launch {
            userAlbumRepository.createUserAlbum(album.albumId, "collection")
        }
    }

    fun addAlbumToWantlist() {
        viewModelScope.launch {
            userAlbumRepository.createUserAlbum(album.albumId, "wantlist")
        }
    }

}