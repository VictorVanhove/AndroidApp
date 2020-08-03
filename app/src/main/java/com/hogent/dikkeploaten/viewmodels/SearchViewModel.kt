package com.hogent.dikkeploaten.viewmodels

import android.util.Log
import androidx.lifecycle.*
import com.hogent.domain.models.Album
import com.hogent.domain.repositories.AlbumRepository
import kotlinx.coroutines.*

enum class ApiStatus { LOADING, ERROR, DONE }

class SearchViewModel internal constructor(
    val albumRepository: AlbumRepository
) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status

    private val _albums = MutableLiveData<List<com.hogent.domain.models.Album>>()

    // The external immutable LiveData for the request status
    val albums: LiveData<List<com.hogent.domain.models.Album>>
        get() = _albums

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedAlbum = MutableLiveData<com.hogent.domain.models.Album>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedAlbum: LiveData<com.hogent.domain.models.Album>
        get() = _navigateToSelectedAlbum

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    /**
     * Call loadAlbumsFromNetwork() on init so we can display status immediately.
     */
    init {
        loadAlbumsFromNetwork()
    }

    /**
     * Gets Mars real estate property information from the API Retrofit service and updates the
     * [Album] [List] and [ApiStatus] [LiveData]. The Retrofit service returns a
     * coroutine Deferred, which we await to get the result of the transaction.
     */
    private fun loadAlbumsFromNetwork()
    {
        viewModelScope.launch {

            setStatus(ApiStatus.LOADING)

            try {

                albumRepository.updateAlbums()

                val albums = albumRepository.getAllAlbums()

                setAlbums(albums)
            } catch (e: Error) {

                Log.e("SearchViewModel", "Error when loading albums", e.cause)
                setStatus(ApiStatus.ERROR)
            } finally {

                setStatus(ApiStatus.DONE)
            }
        }
    }

    private suspend fun setStatus(status: ApiStatus) {
        withContext(Dispatchers.Main) {
            _status.value = status
        }
    }

    private suspend fun setAlbums(albums: List<com.hogent.domain.models.Album>) {
        withContext(Dispatchers.Main) {
            _albums.value = albums
        }
    }

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }

    /**
     * When the album is clicked, set the [_navigateToSelectedAlbum] [MutableLiveData]
     * @param album The [Album] that was clicked on.
     */
    fun displayAlbumDetails(album: com.hogent.domain.models.Album) {
        _navigateToSelectedAlbum.value = album
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedAlbum is set to null
     */
    fun displayAlbumDetailsComplete() {
        _navigateToSelectedAlbum.value = null
    }
}