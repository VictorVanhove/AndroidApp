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
import com.hogent.dikkeploaten.adapters.AlbumAdapter
import com.hogent.dikkeploaten.databinding.FragmentSearchBinding
import com.hogent.dikkeploaten.models.toAlbum
import com.hogent.dikkeploaten.models.toViewAlbum
import com.hogent.dikkeploaten.utilities.InjectorUtils
import com.hogent.dikkeploaten.viewmodels.SearchViewModel

/**
 * Fragment class for the 'Search' tab.
 */
class SearchFragment : Fragment() {

    /**
     * Lazily initialize our [SearchViewModel].
     */
    private val viewModel: SearchViewModel by viewModels {
        InjectorUtils.provideAlbumListViewModelFactory(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSearchBinding.inflate(inflater, container, false)
        context ?: return binding.root

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        val adapter = AlbumAdapter(AlbumAdapter.OnClickListener {
            viewModel.displayAlbumDetails(it.toAlbum())
        })

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.albumList.adapter = adapter

        subscribeUi(adapter)

        viewModel.navigateToSelectedAlbum.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(
                    ViewPagerFragmentDirections.actionViewPagerFragmentToAlbumDetailFragment(it.toViewAlbum())
                )
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayAlbumDetailsComplete()
            }
        })

        binding.buttonReload.setOnClickListener {
            viewModel.loadAlbumsFromNetwork()
        }

        return binding.root
    }

    private fun subscribeUi(adapter: AlbumAdapter) {
        viewModel.albums.observe(viewLifecycleOwner) { result ->
            adapter.submitList(result.map { it.toViewAlbum() })
        }
    }

}