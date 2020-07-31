package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.*
import com.hogent.database.DatabaseAlbum
import com.hogent.dikkeploaten.repositories.AlbumRepository
import kotlinx.coroutines.*

enum class ApiStatus { LOADING, ERROR, DONE }

class SearchViewModel internal constructor(
    val albumRepository: AlbumRepository) : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status

    val albums = albumRepository.albums

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<com.hogent.database.DatabaseAlbum>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<com.hogent.database.DatabaseAlbum>
        get() = _navigateToSelectedProperty

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * Call loadAlbumsFromNetwork() on init so we can display status immediately.
     */
    init {
        loadAlbumsFromNetwork()
    }

    /**
     * Gets Mars real estate property information from the API Retrofit service and updates the
     * [DatabaseAlbum] [List] and [ApiStatus] [LiveData]. The Retrofit service returns a
     * coroutine Deferred, which we await to get the result of the transaction.
     */
    private fun loadAlbumsFromNetwork()
    {
        viewModelScope.launch {
            try {
                _status.value = ApiStatus.LOADING
                albumRepository.updateAlbums()
            } catch (e: Error) {
                _status.value = ApiStatus.ERROR
            } finally {
                _status.value = ApiStatus.DONE
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
     * When the property is clicked, set the [_navigateToSelectedProperty] [MutableLiveData]
     * @param marsProperty The [MarsProperty] that was clicked on.
     */
    fun displayPropertyDetails(album: com.hogent.database.DatabaseAlbum) {
        _navigateToSelectedProperty.value = album
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}