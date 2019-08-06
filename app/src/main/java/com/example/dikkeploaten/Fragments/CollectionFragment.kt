package com.example.dikkeploaten.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
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

        API.shared().getAlbumList { albums ->
            this.albums = albums
            fillRecyclerView(this.albums)
        }

        fillRecyclerView(this.albums)
    }

    private fun fillRecyclerView(albums: ArrayList<Album>) {
        adapter = AlbumAdapter(context!!, albums)
        recyclerView.adapter = adapter
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

}