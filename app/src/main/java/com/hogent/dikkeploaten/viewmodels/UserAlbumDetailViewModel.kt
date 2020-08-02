package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.*
import com.hogent.database.models.AlbumAndUserAlbums
import com.hogent.database.models.UserAlbum
import com.hogent.dikkeploaten.repositories.UserAlbumRepository
import kotlinx.coroutines.launch

/**
 *  The [ViewModel] associated with the [UserAlbumDetailFragment], containing information about the selected
 *  [UserAlbum].
 */
class UserAlbumDetailViewModel(
    private val userAlbumRepository: UserAlbumRepository,
    private val albumProperty: AlbumAndUserAlbums
) : ViewModel() {

    private val _selectedProperty = MutableLiveData<AlbumAndUserAlbums>()

    // The external LiveData for the SelectedProperty
    val selectedProperty: LiveData<AlbumAndUserAlbums>
        get() = _selectedProperty

    private val _inCollection = MutableLiveData<Boolean>()

    // The external immutable LiveData for the request status
    val inCollection: LiveData<Boolean>
        get() = _inCollection

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = albumProperty
        isInCollection()
    }

    fun isInCollection() {
        viewModelScope.launch {
            _inCollection.value = userAlbumRepository.isInCollection(albumProperty.album.albumId)
        }
    }

    fun removeUserAlbumFromCollection() {
        viewModelScope.launch {
            val userAlbum = userAlbumRepository.getUserAlbum(albumProperty.album.albumId)
            userAlbumRepository.removeUserAlbum(userAlbum)
        }
    }

}