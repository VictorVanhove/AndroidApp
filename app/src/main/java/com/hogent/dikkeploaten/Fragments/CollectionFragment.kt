package com.hogent.dikkeploaten.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.hogent.dikkeploaten.adapters.UserAlbumAdapter
import com.hogent.dikkeploaten.databinding.FragmentCollectionBinding
import com.hogent.dikkeploaten.network.Album
import com.hogent.dikkeploaten.utilities.InjectorUtils
import com.hogent.dikkeploaten.viewmodels.UserAlbumListViewModel

/**
 * Fragment class for the 'Collection' tab.
 */
class CollectionFragment : Fragment() {

    private lateinit var binding: FragmentCollectionBinding

    private lateinit var adapter: AlbumAdapter
    private var albums = arrayListOf<Album>()
    /**
     * Lazily initialize our [UserAlbumListViewModel].
     */
    private val viewModel: UserAlbumListViewModel by viewModels {
        InjectorUtils.provideUserAlbumViewModelFactory(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCollectionBinding.inflate(inflater, container, false)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.albumList.adapter = AlbumAdapter(AlbumAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        binding.albumList.adapter = adapter


            }

        return binding.root
    }


}