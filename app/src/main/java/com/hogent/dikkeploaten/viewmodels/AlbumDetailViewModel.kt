package com.hogent.dikkeploaten.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hogent.dikkeploaten.network.AlbumProperty

/**
 *  The [ViewModel] associated with the [DetailFragment], containing information about the selected
 *  [MarsProperty].
 */
class AlbumDetailViewModel(albumProperty: AlbumProperty, app: Application) : AndroidViewModel(app) {
    private val _selectedProperty = MutableLiveData<AlbumProperty>()

    // The external LiveData for the SelectedProperty
    val selectedProperty: LiveData<DatabaseAlbum>
        get() = _selectedProperty

    // Initialize the _selectedProperty MutableLiveData
    init {
        _selectedProperty.value = albumProperty
    }

}