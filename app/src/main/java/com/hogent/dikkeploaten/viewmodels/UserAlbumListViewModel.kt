package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.database.AlbumAndUserAlbums
import com.hogent.dikkeploaten.repositories.UserAlbumRepository

class UserAlbumListViewModel internal constructor(
    userAlbumRepository: UserAlbumRepository
) : ViewModel() {
    val albumAndUserAlbumsCollection: LiveData<List<com.hogent.database.AlbumAndUserAlbums>> =
        userAlbumRepository.getCollectionUser()

    val albumAndUserAlbumsWantlist: LiveData<List<com.hogent.database.AlbumAndUserAlbums>> =
        userAlbumRepository.getWantlistUser()

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<com.hogent.database.AlbumAndUserAlbums>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<com.hogent.database.AlbumAndUserAlbums>
        get() = _navigateToSelectedProperty

    /**
     * When the album is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param album The [AlbumAndUserAlbums] that was clicked on.
     */
    fun displayPropertyDetails(album: com.hogent.database.AlbumAndUserAlbums) {
        _navigateToSelectedProperty.value = album
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}