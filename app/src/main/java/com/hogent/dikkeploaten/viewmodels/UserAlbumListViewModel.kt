package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.database.models.AlbumAndUserAlbums
import com.hogent.dikkeploaten.repositories.UserAlbumRepository
import kotlinx.coroutines.*

class UserAlbumListViewModel internal constructor(
    val userAlbumRepository: UserAlbumRepository
) : ViewModel() {

    private val _albumAndUserAlbumsCollection = MutableLiveData<List<AlbumAndUserAlbums>>()

    // The external immutable LiveData for the request status
    val albumAndUserAlbumsCollection: LiveData<List<AlbumAndUserAlbums>>
        get() = _albumAndUserAlbumsCollection

    private val _albumAndUserAlbumsWantlist = MutableLiveData<List<AlbumAndUserAlbums>>()

    // The external immutable LiveData for the request status
    val albumAndUserAlbumsWantlist: LiveData<List<AlbumAndUserAlbums>>
        get() = _albumAndUserAlbumsWantlist

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<AlbumAndUserAlbums>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<AlbumAndUserAlbums>
        get() = _navigateToSelectedProperty

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the IO dispatcher
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    fun loadAlbumsAndUserAlbums() {
        viewModelScope.launch {
            try {
                _albumAndUserAlbumsCollection.value = userAlbumRepository.getCollectionUser()
                _albumAndUserAlbumsWantlist.value = userAlbumRepository.getWantlistUser()
            } catch (e: Error) {
                throw Error(e)
            }
        }
    }

    private suspend fun updateAlbumsAndUserAlbums() {
        withContext(Dispatchers.IO) {
            try {
                // Make network request using a blocking call
                _albumAndUserAlbumsCollection.value = userAlbumRepository.getCollectionUser()
                _albumAndUserAlbumsWantlist.value = userAlbumRepository.getWantlistUser()
            } catch (cause: Throwable) {
                // If the network throws an exception, inform the caller
                throw Error("Unable to refresh albums", cause)
            }
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * When the album is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param album The [AlbumAndUserAlbums] that was clicked on.
     */
    fun displayPropertyDetails(album: AlbumAndUserAlbums) {
        _navigateToSelectedProperty.value = album
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}