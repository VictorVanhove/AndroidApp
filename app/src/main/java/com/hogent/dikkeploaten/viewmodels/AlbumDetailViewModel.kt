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
 *
 *  @property userAlbumRepository the repository that will handle the adding of the albums
 *  @property album the selected album
 */
class AlbumDetailViewModel(
    private val userAlbumRepository: UserAlbumRepository,
    private val album: Album
) : ViewModel() {

    // The internal MutableLiveData that stores the data of the selected album
    private val _selectedAlbum = MutableLiveData<Album>()

    // The external LiveData for the selected album
    val selectedAlbum: LiveData<Album>
        get() = _selectedAlbum

    // The internal MutableLiveData to check if an album is already in collection/wantlist or not, necessary for buttons
    private val _inCollection = MutableLiveData<Boolean>()

    // The external immutable LiveData for the in collection
    val inCollection: LiveData<Boolean>
        get() = _inCollection

    init {
        // Initialize the _selectedAlbum MutableLiveData
        _selectedAlbum.value = album
        // Update the fab buttons
        isInCollection()
    }

    /**
     * Checks if selected album is already in their collection/wantlist, if so hide buttons.
     */
    private fun isInCollection() {
        viewModelScope.launch {
            _inCollection.value = userAlbumRepository.isInCollection(album.albumId)
        }
    }

    /**
     * Adds the album to the user's collection.
     */
    fun addAlbumToCollection() {
        viewModelScope.launch {
            _inCollection.value = true
            userAlbumRepository.createUserAlbum(album.albumId, "collection")
        }
    }

    /**
     * Adds the album to the user's wantlist.
     */
    fun addAlbumToWantlist() {
        viewModelScope.launch {
            _inCollection.value = true
            userAlbumRepository.createUserAlbum(album.albumId, "wantlist")
        }
    }

}