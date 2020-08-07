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

    private val _selectedAlbum = MutableLiveData<AlbumAndUserAlbums>()

    // The external LiveData for the SelectedAlbum
    val selectedAlbum: LiveData<AlbumAndUserAlbums>
        get() = _selectedAlbum

    private val _inCollection = MutableLiveData<Boolean>()

    // The external immutable LiveData for the request status
    val inCollection: LiveData<Boolean>
        get() = _inCollection

    init {
        // Initialize the _selectedAlbum MutableLiveData
        _selectedAlbum.value = albumProperty
    }

    fun removeUserAlbumFromCollection() {
        viewModelScope.launch {
            val userAlbum = userAlbumRepository.getUserAlbum(albumProperty.album.albumId)
            userAlbumRepository.removeUserAlbum(userAlbum)
        }
    }

}