package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hogent.domain.models.Album
import com.hogent.domain.repositories.UserAlbumRepository
import kotlinx.coroutines.launch

/**
 *  The [ViewModel] associated with the [AlbumDetailFragment], containing information about the selected
 *  [Album].
 */
class AlbumDetailViewModel(
    private val userAlbumRepository: UserAlbumRepository,
    private val album: Album
)   : ViewModel() {

    private val _selectedAlbum = MutableLiveData<Album>()

    // The external LiveData for the SelectedAlbum
    val selectedAlbum: LiveData<Album>
        get() = _selectedAlbum

    private val _inCollection = MutableLiveData<Boolean>()

    // The external immutable LiveData for the request status
    val inCollection: LiveData<Boolean>
        get() = _inCollection

    init {
        // Initialize the _selectedAlbum MutableLiveData
        _selectedAlbum.value = album
        // Update de fab buttons
        isInCollection()
    }

    fun isInCollection() {
        viewModelScope.launch {
            _inCollection.value = userAlbumRepository.isInCollection(album.albumId)
        }
    }

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