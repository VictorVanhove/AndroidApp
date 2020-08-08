package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hogent.domain.models.AlbumAndUserAlbums
import com.hogent.domain.repositories.UserAlbumRepository
import kotlinx.coroutines.launch

/**
 *  The [ViewModel] associated with the [UserAlbumDetailFragment], containing information about the selected
 *  [UserAlbum].
 */
class UserAlbumDetailViewModel(
    private val userAlbumRepository: UserAlbumRepository,
    private val albumProperty: AlbumAndUserAlbums
) : ViewModel() {

    // The internal MutableLiveData that stores the data of the selected user album
    private val _selectedUserAlbum = MutableLiveData<AlbumAndUserAlbums>()

    // The external LiveData for the selected user album
    val selectedUserAlbum: LiveData<AlbumAndUserAlbums>
        get() = _selectedUserAlbum

    init {
        // Initialize the _selectedUserAlbum MutableLiveData
        _selectedUserAlbum.value = albumProperty
    }

    /**
     * Removes the user album from the user's collection or wantlist.
     */
    fun removeUserAlbumFromCollection() {
        viewModelScope.launch {
            val userAlbum = userAlbumRepository.getUserAlbum(albumProperty.album.albumId)
            userAlbumRepository.removeUserAlbum(userAlbum)
        }
    }

}