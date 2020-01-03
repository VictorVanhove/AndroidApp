package com.hogent.dikkeploaten.fragments

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.hogent.dikkeploaten.R
import com.hogent.dikkeploaten.adapters.AlbumAdapter
import com.hogent.dikkeploaten.databinding.FragmentSearchBinding
import com.hogent.dikkeploaten.models.Album
import com.hogent.dikkeploaten.viewmodels.SearchViewModel

/**
 * Fragment class for the 'Search' tab.
 */
class SearchFragment : Fragment(), SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private lateinit var binding: com.hogent.dikkeploaten.databinding.FragmentSearchBinding

    private lateinit var adapter: AlbumAdapter
    private var albums = arrayListOf<Album>()
    private var filteredAlbums = arrayListOf<Album>()
    private lateinit var searchView: SearchView

    /**
     * Lazily initialize our [SearchViewModel].
     */
    private val viewModel: SearchViewModel by lazy {
        ViewModelProviders.of(this).get(SearchViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.albumList.adapter = AlbumAdapter(AlbumAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

        viewModel.navigateToSelectedProperty.observe(this, Observer {
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

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
////        API.shared.getAlbumList { albums ->
////            this.albums = albums
////            fillRecyclerView(this.albums)
////            disableLoadingScreen()
////        }
////
////        fillRecyclerView(this.albums)
////        initSwipe()
//    }

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
        filteredAlbums = filter(albums, query)
        adapter.setFilter(filteredAlbums)

        return true
    }

    /**
     * Filters album list to filtered albums matching the query.
     */
    private fun filter(mList: ArrayList<Album>, query: String): ArrayList<Album> {
        val filteredList = ArrayList<Album>()
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
        adapter.setFilter(albums)
        return true
    }

//    /**
//     * Fills the recyclerView with albums.
//     */
//    private fun fillRecyclerView(albums: ArrayList<Album>) {
//        if (recyclerView != null) {
//            adapter = AlbumAdapter(context!!, albums, true)
//            recyclerView.adapter = adapter
//            recyclerView.setHasFixedSize(true)
//            recyclerView.layoutManager = LinearLayoutManager(context)
//        }
//    }

    /**
     * Initializes swipe functionality to items in list.
     */
//    private fun initSwipe() {
//        val simpleItemTouchCallback =
//            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//
//                override fun onMove(
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    target: RecyclerView.ViewHolder
//                ): Boolean {
//                    return false
//                }
//
//                // Manages the swipe functionality
//                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                    val position = viewHolder.adapterPosition
//                    val album: Album
//
//                    album = if (searchView.isIconified) {
//                        albums[position]
//                    } else {
//                        filteredAlbums[position]
//                    }
//
//                    val text: String
//
//                    if (direction == ItemTouchHelper.LEFT) {
////                        API.shared.addCollectionAlbum(album.id)
//                        text = "Album is toegevoegd aan je collectie!"
//                    } else {
////                        API.shared.addWantlistAlbum(album.id)
//                        text = "Album is toegevoegd aan je wantlist!"
//                    }
//
//                    adapter.notifyDataSetChanged()
//                    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
//                }
//
//                // Adds the background to the swipe
//                override fun onChildDraw(
//                    c: Canvas,
//                    recyclerView: RecyclerView,
//                    viewHolder: RecyclerView.ViewHolder,
//                    dX: Float,
//                    dY: Float,
//                    actionState: Int,
//                    isCurrentlyActive: Boolean
//                ) {
//                    val icon: Drawable
//                    val iconBackground: ColorDrawable
//
//                    val itemView = viewHolder.itemView
//
//                    if (dX > 0) {
//                        icon = ContextCompat.getDrawable(adapter.context, R.drawable.ic_favorite_white_24dp)!!
//                        iconBackground = ColorDrawable(Color.parseColor("#ffa500"))
//
//                        val iconMarginVertical = (viewHolder.itemView.height - icon.intrinsicHeight) / 2
//
//                        iconBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
//                        icon.setBounds(
//                            itemView.left + iconMarginVertical,
//                            itemView.top + iconMarginVertical,
//                            itemView.left + iconMarginVertical + icon.intrinsicWidth,
//                            itemView.bottom - iconMarginVertical
//                        )
//                    } else {
//                        icon = ContextCompat.getDrawable(adapter.context, R.drawable.ic_dehaze_white_24dp)!!
//                        iconBackground = ColorDrawable(Color.parseColor("#008000"))
//
//                        val iconMarginVertical = (viewHolder.itemView.height - icon.intrinsicHeight) / 2
//
//                        iconBackground.setBounds(
//                            itemView.right + dX.toInt(),
//                            itemView.top,
//                            itemView.right,
//                            itemView.bottom
//                        )
//                        icon.setBounds(
//                            itemView.right - iconMarginVertical - icon.intrinsicWidth,
//                            itemView.top + iconMarginVertical,
//                            itemView.right - iconMarginVertical,
//                            itemView.bottom - iconMarginVertical
//                        )
//                        icon.level = 0
//                    }
//
//                    iconBackground.draw(c)
//
//                    c.save()
//
//                    if (dX > 0)
//                        c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
//                    else
//                        c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
//
//                    icon.draw(c)
//
//                    c.restore()
//
//                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                }
//
//                // Disables swipe if album is already in collection/wantlist
//                override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
//                    val position = viewHolder.adapterPosition
//                    val album: Album
//
//                    if (searchView.isIconified) {
//                        album = albums[position]
//                    } else {
//                        album = filteredAlbums[position]
//                    }
//
////                    if (API.shared.cache.user.plates.any { userAlbum -> userAlbum.albumID == album.id } || API.shared.cache.user.wantList.any { userAlbum -> userAlbum.albumID == album.id }) {
//                        return makeMovementFlags(0, 0)
////                    } else {
////                        val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
////                        return makeMovementFlags(0, swipeFlags)
////                    }
//                }
//            }
//        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
//        itemTouchHelper.attachToRecyclerView(binding.albumList)
//    }

    /**
     * Disables progressBar.
     */
//    private fun disableLoadingScreen() {
//        if (progressBar != null) {
//            progressBar.visibility = View.GONE
//        }
//    }

}