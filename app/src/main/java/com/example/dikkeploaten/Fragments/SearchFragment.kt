package com.example.dikkeploaten.Fragments

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dikkeploaten.Adapters.AlbumAdapter
import com.example.dikkeploaten.Models.Album
import com.example.dikkeploaten.R
import com.example.dikkeploaten.Services.API
import kotlinx.android.synthetic.main.fragment_collection.*

class SearchFragment : Fragment(), SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener {

    private lateinit var adapter: AlbumAdapter
    private var albums = arrayListOf<Album>()
    private var filteredAlbums = arrayListOf<Album>()
    private lateinit var searchView: SearchView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        API.shared.getAlbumList { albums ->
            this.albums = albums
            fillRecyclerView(this.albums)
            disableLoadingScreen()
        }

        fillRecyclerView(this.albums)
        initSwipe()
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

    /**
     * Fills the recyclerView with albums.
     */
    private fun fillRecyclerView(albums: ArrayList<Album>) {
        if (recyclerView != null) {
            adapter = AlbumAdapter(context!!, albums, true)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    /**
     * Initializes swipe functionality to items in list.
     */
    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                var album: Album

                if (searchView.isIconified) {
                    album = albums[position]
                } else {
                    album = filteredAlbums[position]
                }

                var text: String

                if (direction == ItemTouchHelper.LEFT) {
                    API.shared.addCollectionAlbum(album.id)
                    text = "Album successfully added to collection!"
                } else {
                    API.shared.addWantlistAlbum(album.id)
                    text = "Album successfully added to wantlist!"
                }

                adapter.notifyDataSetChanged()
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
            ) {
                var icon: Drawable
                var iconBackground: ColorDrawable

                val itemView = viewHolder.itemView

                if (dX > 0) {
                    icon = ContextCompat.getDrawable(adapter.context, R.drawable.ic_favorite_white_24dp)!!
                    iconBackground = ColorDrawable(Color.parseColor("#ffa500"))

                    val iconMarginVertical = (viewHolder.itemView.height - icon.intrinsicHeight) / 2

                    iconBackground.setBounds(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                    icon.setBounds(itemView.left + iconMarginVertical, itemView.top + iconMarginVertical,
                        itemView.left + iconMarginVertical + icon.intrinsicWidth, itemView.bottom - iconMarginVertical)
                } else {
                    icon = ContextCompat.getDrawable(adapter.context, R.drawable.ic_dehaze_white_24dp)!!
                    iconBackground = ColorDrawable(Color.parseColor("#008000"))

                    val iconMarginVertical = (viewHolder.itemView.height - icon.intrinsicHeight) / 2

                    iconBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                    icon.setBounds(itemView.right - iconMarginVertical - icon.intrinsicWidth, itemView.top + iconMarginVertical,
                        itemView.right - iconMarginVertical, itemView.bottom - iconMarginVertical)
                    icon.level = 0
                }

                iconBackground.draw(c)

                c.save()

                if (dX > 0)
                    c.clipRect(itemView.left, itemView.top, dX.toInt(), itemView.bottom)
                else
                    c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)

                icon.draw(c)

                c.restore()

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val position = viewHolder.adapterPosition
                var album: Album

                if (searchView.isIconified) {
                    album = albums[position]
                } else {
                    album = filteredAlbums[position]
                }

                // Set movement flags based on the layout manager
                if (API.shared.cache.user.plates!!.any { userAlbum -> userAlbum.albumID == album.id } || API.shared.cache.user.wantList!!.any { userAlbum -> userAlbum.albumID == album.id }) {
                    return makeMovementFlags(0, 0)
                } else {
                    val swipeFlags = ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                    return makeMovementFlags(0, swipeFlags)
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    /**
     * Disables progressBar.
     */
    private fun disableLoadingScreen() {
        if(progressBar != null) {
            progressBar.visibility = View.GONE
        }
    }

}