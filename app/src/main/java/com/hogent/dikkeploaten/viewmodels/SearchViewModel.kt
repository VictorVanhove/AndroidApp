package com.hogent.dikkeploaten.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.dikkeploaten.network.AlbumProperty
import com.hogent.dikkeploaten.network.Api
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<ApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<ApiStatus>
        get() = _status

    // Internally, we use a MutableLiveData, because we will be updating the List of MarsProperty
    // with new values
    private val _properties = MutableLiveData<List<DatabaseAlbum>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<DatabaseAlbum>>
        get() = _properties

    val albums: LiveData<List<DatabaseAlbum>> = albumRepository.getAlbums()

    // Internally, we use a MutableLiveData to handle navigation to the selected property
    private val _navigateToSelectedProperty = MutableLiveData<DatabaseAlbum>()

    // The external immutable LiveData for the navigation property
    val navigateToSelectedProperty: LiveData<DatabaseAlbum>
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
            // Get the Deferred object for our Retrofit request
            var getAlbumsDeferred = Api.retrofitService.getAlbumList()
            try {
                _status.value = ApiStatus.LOADING
                // this will run on a thread managed by Retrofit
                val listResult = getAlbumsDeferred.await()
                _status.value = ApiStatus.DONE
                _properties.value = listResult
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _properties.value = ArrayList()
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
    fun displayPropertyDetails(album: DatabaseAlbum) {
        _navigateToSelectedProperty.value = album
    }

    /**
     * After the navigation has taken place, make sure navigateToSelectedProperty is set to null
     */
    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }
}