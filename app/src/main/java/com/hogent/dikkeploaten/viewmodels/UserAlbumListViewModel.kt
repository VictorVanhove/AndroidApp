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

    // The external immutable LiveData for the request userAlbums
    val albumAndUserAlbumsCollection: LiveData<List<AlbumAndUserAlbums>>
        get() = _albumAndUserAlbumsCollection

    private val _albumAndUserAlbumsWantlist = MutableLiveData<List<AlbumAndUserAlbums>>()

    // The external immutable LiveData for the request userAlbums
    val albumAndUserAlbumsWantlist: LiveData<List<AlbumAndUserAlbums>>
        get() = _albumAndUserAlbumsWantlist

    // Internally, we use a MutableLiveData to handle navigation to the selected album
    private val _navigateToSelectedAlbum = MutableLiveData<AlbumAndUserAlbums>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedAlbum: LiveData<AlbumAndUserAlbums>
        get() = _navigateToSelectedAlbum

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

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    /**
     * When the album is clicked, set the [_navigateToSelectedAlbum] [MutableLiveData]
     * @param album The [AlbumAndUserAlbums] that was clicked on.
     */
    fun displayAlbumDetails(album: AlbumAndUserAlbums) {
        _navigateToSelectedAlbum.value = album
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedAlbum is set to null
     */
    fun displayAlbumDetailsComplete() {
        _navigateToSelectedAlbum.value = null
    }
}