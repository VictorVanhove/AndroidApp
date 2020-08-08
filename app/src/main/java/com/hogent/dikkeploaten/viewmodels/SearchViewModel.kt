package com.hogent.dikkeploaten.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.domain.models.Album
import com.hogent.domain.repositories.AlbumRepository
import kotlinx.coroutines.*

enum class ApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] associated with the [SearchFragment], containing a list of albums.
 *
 * @property albumRepository the repository that will process and import all the albums
 */
class SearchViewModel internal constructor(
    val albumRepository: AlbumRepository
) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status

    // The internal MutableLiveData for albums, gets then loaded into recyclerview
    private val _albums = MutableLiveData<List<Album>>()

    // The external immutable LiveData for albums
    val albums: LiveData<List<Album>>
        get() = _albums

    // The internal MutableLiveData to handle navigation to the selected album
    private val _navigateToSelectedAlbum = MutableLiveData<Album>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedAlbum: LiveData<Album>
        get() = _navigateToSelectedAlbum

    // Initialization of a job to be able to cancel when needed
    private var viewModelJob = Job()

    // Init the coroutine with job and run it on a background thread (IO) dispatcher to reduce work on the main UI
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.IO)

    /**
     * Call loadAlbumsFromNetwork() on init so we can display status immediately.
     */
    init {
        loadAlbumsFromNetwork()
    }

    /**
     * Gets album information from the API Retrofit service and updates the
     * [Album] [List] and [ApiStatus] [LiveData]. The Retrofit service returns a
     * coroutine suspend function, which we then pass on to update the albums.
     */
    fun loadAlbumsFromNetwork() {
        viewModelScope.launch {

            setStatus(ApiStatus.LOADING)

            try {

                albumRepository.updateAlbums()

                val albums = albumRepository.getAllAlbums()

                setAlbums(albums)

                setStatus(ApiStatus.DONE)
            } catch (e: Error) {

                Log.e("SearchViewModel", "Error when loading albums", e.cause)
                setStatus(ApiStatus.ERROR)
            }
        }
    }

    /**
     * When background thread is completed, run the main UI and update the status.
     * @param status The [ApiStatus] of the current state
     */
    private suspend fun setStatus(status: ApiStatus) {
        withContext(Dispatchers.Main) {
            _status.value = status
        }
    }

    /**
     * When background thread is completed do this also for the albums, run the main UI and update the albums.
     * @param albums The [Album] [List] of the received network request
     */
    private suspend fun setAlbums(albums: List<Album>) {
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
    fun displayAlbumDetails(album: Album) {
        _navigateToSelectedAlbum.value = album
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedAlbum is set to null.
     */
    fun displayAlbumDetailsComplete() {
        _navigateToSelectedAlbum.value = null
    }
}