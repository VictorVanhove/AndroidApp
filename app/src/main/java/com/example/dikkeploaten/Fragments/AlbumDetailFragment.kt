package com.example.dikkeploaten.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.dikkeploaten.Models.Album
import com.example.dikkeploaten.R
import kotlinx.android.synthetic.main.fragment_album_info.*
import kotlinx.android.synthetic.main.layout_albumitem.artist_album
import kotlinx.android.synthetic.main.layout_albumitem.title_album

class AlbumDetailFragment: Fragment() {

    private lateinit var album: Album

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        title_album.text = album.title
        artist_album.text = album.artist
        description_album.text = album.description
        genre_album.text = album.genre
        release_album.text = album.released_in
        tracklist_album.text = album.tracklist
        musicians_album.text = album.musicians

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context!!).load(album.thumb).apply(requestOptions).into(image_album)

    }

    fun initiateAttributes(album: Album) {
        this.album = album
    }

}