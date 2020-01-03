package com.hogent.dikkeploaten.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.hogent.dikkeploaten.adapters.AlbumAdapter
import com.hogent.dikkeploaten.databinding.FragmentWantlistBinding
import com.hogent.dikkeploaten.models.Album
import com.hogent.dikkeploaten.viewmodels.CollectionViewModel
import com.hogent.dikkeploaten.viewmodels.WantlistViewModel
import kotlinx.android.synthetic.main.fragment_collection.*
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Fragment class for the 'Wantlist' tab.
 */
class WantlistFragment : Fragment() {

    private lateinit var adapter: AlbumAdapter
    private var albums = arrayListOf<Album>()

    /**
     * Lazily initialize our [CollectionViewModel].
     */
    private val viewModel: WantlistViewModel by lazy {
        ViewModelProviders.of(this).get(WantlistViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = FragmentWantlistBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel

        // Sets the adapter of the photosGrid RecyclerView with clickHandler lambda that
        // tells the viewModel when our property is clicked
        binding.albumList.adapter = AlbumAdapter(AlbumAdapter.OnClickListener {
            viewModel.displayPropertyDetails(it)
        })

//        viewModel.navigateToSelectedProperty.observe(this, Observer {
//            if (null != it) {
//                // Must find the NavController from the Fragment
//                this.findNavController().navigate(CollectionFragmentDirections.actionShowDetail(it))
//                // Tell the ViewModel we've made the navigate call to prevent multiple navigation
//                viewModel.displayPropertyDetailsComplete()
//            }
//        })

        return binding.root
    }

//    override fun onActivityCreated(savedInstanceState: Bundle?) {
//        super.onActivityCreated(savedInstanceState)
//
////        API.shared.getUserWantlist { albums ->
////            this.albums = albums
////            fillRecyclerView(this.albums)
////            disableExtraScreens()
////        }
////
////        fillRecyclerView(this.albums)
////        checkWantlistStatus()
////        initSwipe()
//    }

    /**
     * Fills the recyclerView with albums.
     */
//    private fun fillRecyclerView(albums: ArrayList<Album>) {
//        if (recyclerView != null) {
//            adapter = AlbumAdapter(context!!, albums, false)
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
//            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
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
//                    val album = albums[position]
//                    albums.removeAt(position)
//
//                    if (direction == ItemTouchHelper.LEFT) {
// //                       API.shared.deleteWantlistAlbum(album.id) {
// //                           checkWantlistStatus()
// //                       }
//                        adapter.notifyDataSetChanged()
//                    }
//
//                    Toast.makeText(context, "Album is verwijderd van je wantlist!", Toast.LENGTH_SHORT).show()
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
//                    val deleteIcon = ContextCompat.getDrawable(adapter.context, R.drawable.ic_delete_white_24dp)!!
//                    val deleteIconBackground = ColorDrawable(Color.parseColor("#ff0000"))
//
//                    val itemView = viewHolder.itemView
//                    val iconMarginVertical = (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2
//
//                    if (dX < 0) {
//                        deleteIconBackground.setBounds(
//                            itemView.right + dX.toInt(),
//                            itemView.top,
//                            itemView.right,
//                            itemView.bottom
//                        )
//                        deleteIcon.setBounds(
//                            itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth,
//                            itemView.top + iconMarginVertical,
//                            itemView.right - iconMarginVertical,
//                            itemView.bottom - iconMarginVertical
//                        )
//                        deleteIcon.level = 0
//                    }
//
//                    deleteIconBackground.draw(c)
//
//                    c.save()
//
//                    if (dX < 0)
//                        c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
//
//                    deleteIcon.draw(c)
//
//                    c.restore()
//
//                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                }
//            }
//        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
//        itemTouchHelper.attachToRecyclerView(recyclerView)
//    }

    /**
     * Checks if user's wantlist is empty.
     */
    private fun checkWantlistStatus() {
//        if (API.shared.cache.user.wantList.isEmpty()) {
//            progressBar.visibility = View.GONE
//            emptyMessage.visibility = View.VISIBLE
//        }
    }

    /**
     * Disables progressBar and emptyMessage.
     */
    private fun disableExtraScreens() {
        if (progressBar != null) {
            progressBar.visibility = View.GONE
            emptyMessage.visibility = View.GONE
        }
    }

}