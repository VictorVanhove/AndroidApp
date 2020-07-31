package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.*
import com.hogent.database.AlbumAndUserAlbums
import com.hogent.database.UserAlbum
import com.hogent.dikkeploaten.repositories.UserAlbumRepository
import kotlinx.coroutines.launch

/**
 *  The [ViewModel] associated with the [UserAlbumDetailFragment], containing information about the selected
 *  [UserAlbum].
 */
class UserAlbumDetailViewModel(
    private val userAlbumRepository: UserAlbumRepository,
    private val albumProperty: com.hogent.database.AlbumAndUserAlbums
) : ViewModel() {
    private val _selectedProperty = MutableLiveData<com.hogent.database.AlbumAndUserAlbums>()

    // The external LiveData for the SelectedProperty
    val selectedProperty: LiveData<com.hogent.database.AlbumAndUserAlbums>
        get() = _selectedProperty

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = albumProperty
    }

    val isInCollection = userAlbumRepository.isInCollection(albumProperty.album.albumId)

    fun removeUserAlbumFromCollection() {
        viewModelScope.launch {
            val userAlbum = userAlbumRepository.getUserAlbum(albumProperty.album.albumId)
            userAlbumRepository.removeUserAlbum(userAlbum)
        }
    }

}