package com.example.dikkeploaten.Fragments

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
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

class SearchFragment : Fragment() {

    private lateinit var adapter: AlbumAdapter
    private var albums = arrayListOf<Album>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        API.shared().getAlbumList { albums ->
            this.albums = albums
            fillRecyclerView(this.albums)
        }

        fillRecyclerView(this.albums)
        initSwipe()
    }

    private fun fillRecyclerView(albums: ArrayList<Album>) {
        adapter = AlbumAdapter(context!!, albums)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val album = albums[position]
                var text: String

                if (direction == ItemTouchHelper.LEFT) {
                    API.shared().addCollectionAlbum(album.id)
                    text = "Album successfully added to collection!"
                    adapter.notifyDataSetChanged()
                } else {
                    API.shared().addWantlistAlbum(album.id)
                    text = "Album successfully added to wantlist!"
                    adapter.notifyDataSetChanged()
                }

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
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

}