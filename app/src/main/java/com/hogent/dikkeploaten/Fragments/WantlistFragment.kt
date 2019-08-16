package com.hogent.dikkeploaten.fragments

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hogent.dikkeploaten.R
import com.hogent.dikkeploaten.adapters.AlbumAdapter
import com.hogent.dikkeploaten.models.Album
import com.hogent.dikkeploaten.services.API
import kotlinx.android.synthetic.main.fragment_collection.*

class WantlistFragment : Fragment() {

    private lateinit var adapter: AlbumAdapter
    private var albums = arrayListOf<Album>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wantlist, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        API.shared.getUserWantlist { albums ->
            this.albums = albums
            fillRecyclerView(this.albums)
            disableExtraScreens()
        }

        fillRecyclerView(this.albums)
        checkWantlistStatus()
        initSwipe()
    }

    /**
     * Fills the recyclerView with albums.
     */
    private fun fillRecyclerView(albums: ArrayList<Album>) {
        if (recyclerView != null) {
            adapter = AlbumAdapter(context!!, albums, false)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(context)
        }
    }

    /**
     * Initializes swipe functionality to items in list.
     */
    private fun initSwipe() {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val album = albums[position]
                    albums.removeAt(position)

                    if (direction == ItemTouchHelper.LEFT) {
                        API.shared.deleteWantlistAlbum(album.id) {
                            checkWantlistStatus()
                        }
                        adapter.notifyDataSetChanged()
                    }

                    Toast.makeText(context, "Album is verwijderd van je wantlist!", Toast.LENGTH_SHORT).show()
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    val deleteIcon = ContextCompat.getDrawable(adapter.context, R.drawable.ic_delete_white_24dp)!!
                    val deleteIconBackground = ColorDrawable(Color.parseColor("#ff0000"))

                    val itemView = viewHolder.itemView
                    val iconMarginVertical = (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2

                    if (dX < 0) {
                        deleteIconBackground.setBounds(
                            itemView.right + dX.toInt(),
                            itemView.top,
                            itemView.right,
                            itemView.bottom
                        )
                        deleteIcon.setBounds(
                            itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth,
                            itemView.top + iconMarginVertical,
                            itemView.right - iconMarginVertical,
                            itemView.bottom - iconMarginVertical
                        )
                        deleteIcon.level = 0
                    }

                    deleteIconBackground.draw(c)

                    c.save()

                    if (dX < 0)
                        c.clipRect(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)

                    deleteIcon.draw(c)

                    c.restore()

                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    /**
     * Checks if user's wantlist is empty.
     */
    private fun checkWantlistStatus() {
        if (API.shared.cache.user.wantList.isEmpty()) {
            progressBar.visibility = View.GONE
            emptyMessage.visibility = View.VISIBLE
        }
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