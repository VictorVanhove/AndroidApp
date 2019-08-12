package com.example.dikkeploaten.Fragments

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
import com.example.dikkeploaten.Adapters.AlbumAdapter
import com.example.dikkeploaten.Models.Album
import com.example.dikkeploaten.R
import com.example.dikkeploaten.Services.API
import kotlinx.android.synthetic.main.fragment_collection.*

class CollectionFragment : Fragment() {

    private lateinit var adapter: AlbumAdapter
    private var albums = arrayListOf<Album>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        API.shared.getUserCollection { albums ->
            this.albums = albums
            fillRecyclerView(this.albums)
        }

        fillRecyclerView(this.albums)
        initSwipe()
    }

    private fun fillRecyclerView(albums: ArrayList<Album>) {
        adapter = AlbumAdapter(context!!, albums, false)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initSwipe() {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

                override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    val position = viewHolder.adapterPosition
                    val album = albums[position]
                    albums.removeAt(position)

                    if (direction == ItemTouchHelper.LEFT) {
                        API.shared.deleteCollectionAlbum(album.id)
                        adapter.notifyDataSetChanged()
                    }

                    Toast.makeText(context, "Album successfully removed from collection!", Toast.LENGTH_SHORT).show()
                }

                override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean
                ) {
                    var deleteIcon = ContextCompat.getDrawable(adapter.context, R.drawable.ic_delete_white_24dp)!!
                    var deleteIconBackground = ColorDrawable(Color.parseColor("#ff0000"))

                    val itemView = viewHolder.itemView
                    val iconMarginVertical = (viewHolder.itemView.height - deleteIcon.intrinsicHeight) / 2

                    if (dX < 0) {
                        deleteIconBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
                        deleteIcon.setBounds(itemView.right - iconMarginVertical - deleteIcon.intrinsicWidth, itemView.top + iconMarginVertical,
                            itemView.right - iconMarginVertical, itemView.bottom - iconMarginVertical)
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

}