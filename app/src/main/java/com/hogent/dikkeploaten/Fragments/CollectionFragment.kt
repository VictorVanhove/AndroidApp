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
import com.hogent.dikkeploaten.utilities.InjectorUtils
import com.hogent.dikkeploaten.viewmodels.UserAlbumListViewModel

/**
 * Fragment class for the 'Collection' tab.
 */
class CollectionFragment : Fragment() {

    private lateinit var binding: FragmentCollectionBinding

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
        val adapter = UserAlbumAdapter(UserAlbumAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        binding.albumList.adapter = adapter

        viewModel.loadAlbumsAndUserAlbums()
        subscribeUi(adapter, binding)

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(
                    ViewPagerFragmentDirections.actionViewPagerFragmentToUserAlbumDetailFragment(it)
                )
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation /
                viewModel.displayPropertyDetailsComplete()
            }
        })

        return binding.root
    }

    private fun subscribeUi(adapter: UserAlbumAdapter, binding: FragmentCollectionBinding) {
        viewModel.albumAndUserAlbumsCollection.observe(viewLifecycleOwner) { result ->
            binding.hasAlbums = !result.isNullOrEmpty()
            adapter.submitList(result)
        }
    }

}