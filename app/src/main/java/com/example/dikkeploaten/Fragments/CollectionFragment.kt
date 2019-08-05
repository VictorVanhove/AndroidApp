package com.example.dikkeploaten.Fragments

import android.os.Bundle
    import android.view.LayoutInflater
    import android.view.View
    import android.view.ViewGroup
    import androidx.annotation.NonNull
    import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dikkeploaten.Adapters.AlbumAdapter
import com.example.dikkeploaten.Models.Album
import com.example.dikkeploaten.R
import kotlinx.android.synthetic.main.fragment_collection.*

class CollectionFragment : Fragment() {

        private lateinit var adapter: AlbumAdapter

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_collection, container, false)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)

            val albums = listOf(
                Album(0,"Highway To Hell","ACDC","bla"),
                Album(1,"Rumours","Fleetwood Mac","bla"),
                Album(2,"Heroes","David Bowie","bla"),
                Album(3,"1999","Prince","bla"),
                Album(4,"Thriller","Michael Jackson","bla"),
                Album(5,"Faith","George Michael","bla"),
                Album(6,"Make It Big","Wham!","bla")
            )

            adapter = AlbumAdapter(context!!, albums)
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(context)
        }

}