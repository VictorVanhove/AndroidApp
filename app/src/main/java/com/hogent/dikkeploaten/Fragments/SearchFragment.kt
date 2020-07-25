package com.hogent.dikkeploaten.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import com.hogent.dikkeploaten.R
import com.hogent.dikkeploaten.adapters.AlbumAdapter
import com.hogent.dikkeploaten.database.DatabaseAlbum
import com.hogent.dikkeploaten.databinding.FragmentSearchBinding
import com.hogent.dikkeploaten.utilities.InjectorUtils
import com.hogent.dikkeploaten.viewmodels.SearchViewModel

/**
 * Fragment class for the 'Search' tab.
 */
class SearchFragment : Fragment(), SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    //private lateinit var adapter: AlbumAdapter
    //private var albums = arrayListOf<Album>()
    //private var filteredAlbums = arrayListOf<Album>()
    private lateinit var searchView: SearchView

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
            viewModel.displayPropertyDetails(it)
        })

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.albumList.adapter = adapter

        viewModel.navigateToSelectedProperty.observe(viewLifecycleOwner, Observer {
            if (null != it) {
                // Must find the NavController from the Fragment
                this.findNavController().navigate(SearchFragmentDirections.actionShowDetail(it))
                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
                viewModel.displayPropertyDetailsComplete()
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    /**
     * Inflates the settings menu item into the actionbar.
     */
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.searchbar_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)

        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Checks if query is changed, if so, filter gets initialized.
     */
    override fun onQueryTextChange(query: String): Boolean {
//        filteredAlbums = filter(albums, query)
//        adapter.setFilter(filteredAlbums)
//
        return true
    }

    /**
     * Filters album list to filtered albums matching the query.
     */
    private fun filter(mList: ArrayList<DatabaseAlbum>, query: String): ArrayList<DatabaseAlbum> {
        val filteredList = ArrayList<DatabaseAlbum>()
        for (item in mList) {
            val title = item.title.toLowerCase()
            val author = item.artist.toLowerCase()
            if (title.contains(query.toLowerCase()) || author.contains(query.toLowerCase())) {
                filteredList.add(item)
            }
        }

        return filteredList
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        return false
    }

    override fun onMenuItemActionExpand(item: MenuItem): Boolean {
        return true
    }

    override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
        //adapter.setFilter(albums)
        return true
    }

}