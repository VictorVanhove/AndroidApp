package com.hogent.dikkeploaten.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.hogent.dikkeploaten.models.Album
import com.hogent.dikkeploaten.viewmodels.AlbumDetailViewModel
import com.hogent.dikkeploaten.viewmodels.AlbumDetailViewModelFactory

/**
 * Fragment class for information page of each album.
 */
class AlbumDetailFragment : Fragment() {

    private lateinit var album: Album

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val application = requireNotNull(activity).application
        val binding = com.hogent.dikkeploaten.databinding.FragmentAlbumInfoBinding.inflate(inflater)
        binding.lifecycleOwner = this
        val albumProperty = AlbumDetailFragmentArgs.fromBundle(arguments!!).selectedProperty
        val viewModelFactory = AlbumDetailViewModelFactory(albumProperty, application)
        binding.viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(AlbumDetailViewModel::class.java)
        return binding.root
    }

}