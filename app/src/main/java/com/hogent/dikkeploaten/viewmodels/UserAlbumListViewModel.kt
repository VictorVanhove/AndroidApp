package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.domain.models.AlbumAndUserAlbums
import com.hogent.domain.repositories.UserAlbumRepository
import kotlinx.coroutines.*

/**
 * The ViewModel used in [CollectionFragment] and [WantlistFragment].
 */
class UserAlbumListViewModel internal constructor(
    val userAlbumRepository: UserAlbumRepository
) : ViewModel() {

    // The internal MutableLiveData that stores the user albums of collection
    private val _albumAndUserAlbumsCollection = MutableLiveData<List<AlbumAndUserAlbums>>()

    // The external immutable LiveData for user albums in collection
    val albumAndUserAlbumsCollection: LiveData<List<AlbumAndUserAlbums>>
        get() = _albumAndUserAlbumsCollection

    // The internal MutableLiveData that stores the user albums of wantlist
    private val _albumAndUserAlbumsWantlist = MutableLiveData<List<AlbumAndUserAlbums>>()

    // The external immutable LiveData for the user albums in wantlist
    val albumAndUserAlbumsWantlist: LiveData<List<AlbumAndUserAlbums>>
        get() = _albumAndUserAlbumsWantlist

    // The internal MutableLiveData to handle navigation to the selected user album
    private val _navigateToSelectedUserAlbum = MutableLiveData<AlbumAndUserAlbums>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedUserAlbum: LiveData<AlbumAndUserAlbums>
        get() = _navigateToSelectedUserAlbum

    // Initialization of a job to be able to cancel when needed
    private var viewModelJob = Job()

    // Init the coroutine with job and run it on a background thread (IO) dispatcher to reduce work on the main UI
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)


    fun loadAlbumsAndUserAlbums() {
        viewModelScope.launch {
            try {

                val collection = userAlbumRepository.getCollectionUser()
                val wantList = userAlbumRepository.getWantlistUser()

                withContext(Dispatchers.Main) {

                    _albumAndUserAlbumsCollection.value = collection
                    _albumAndUserAlbumsWantlist.value = wantList
                }
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
     * When the album is clicked, set the [_navigateToSelectedUserAlbum] [MutableLiveData]
     * @param album The [AlbumAndUserAlbums] that was clicked on.
     */
    fun displayAlbumDetails(album: AlbumAndUserAlbums) {
        _navigateToSelectedUserAlbum.value = album
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedAlbum is set to null
     */
    fun displayAlbumDetailsComplete() {
        _navigateToSelectedUserAlbum.value = null
    }
}