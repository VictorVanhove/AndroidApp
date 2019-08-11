package com.example.dikkeploaten.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.dikkeploaten.Activities.MainActivity
import com.example.dikkeploaten.Models.Album
import com.example.dikkeploaten.R
import kotlinx.android.synthetic.main.layout_albumitem.view.*

class AlbumAdapter(var context: Context, var albums: ArrayList<Album>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_albumitem, parent, false)
        return AlbumViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.txt_title.text = album.title
        holder.txt_artist.text = album.artist

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context).load(album.thumb).apply(requestOptions).into(holder.image)

        holder.itemView.setOnClickListener {
            run {
                if (context is MainActivity)
                {
                    val activity = context as MainActivity
                    activity.goToAlbumDetail(album)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return albums.size
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txt_title = itemView.title_album
        val txt_artist = itemView.artist_album
        val image = itemView.image_album

    }

    fun setFilter(newsArrayList: ArrayList<Album>) {
        this.albums = newsArrayList
        notifyDataSetChanged()
    }
}


