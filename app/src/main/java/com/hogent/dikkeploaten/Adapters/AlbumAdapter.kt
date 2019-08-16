package com.hogent.dikkeploaten.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.hogent.dikkeploaten.R
import com.hogent.dikkeploaten.activities.MainActivity
import com.hogent.dikkeploaten.models.Album
import com.hogent.dikkeploaten.services.API
import kotlinx.android.synthetic.main.layout_albumitem.view.*

/**
 * Adapter class for each album in recyclerView.
 */
class AlbumAdapter(var context: Context, private var albums: ArrayList<Album>, private var showStatus: Boolean) :
    RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    /**
     * Inflates the AlbumViewHolder.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_albumitem, parent, false
        )
        return AlbumViewHolder(itemView)
    }

    /**
     * Binds each album in the ArrayList to a view.
     */
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.txtTitle.text = album.title
        holder.txtArtist.text = album.artist

        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(context).load(album.thumb).apply(requestOptions).into(holder.imageAlbum)

        //Adds status image if album belongs to collection/wantlist
        if (showStatus) {
            when {
                API.shared.cache.user.plates.any { userAlbum -> userAlbum.albumID == album.id } -> Glide.with(context).load(
                    R.mipmap.ic_in_collection
                ).apply(requestOptions).into(holder.imageStatus)
                API.shared.cache.user.wantList.any { userAlbum -> userAlbum.albumID == album.id } -> Glide.with(context).load(
                    R.mipmap.ic_in_wantlist
                ).apply(requestOptions).into(holder.imageStatus)
                else -> Glide.with(context).load(R.drawable.ic_not_in_collection).apply(requestOptions).into(holder.imageStatus)
            }
        }

        holder.itemView.setOnClickListener {
            run {
                if (context is MainActivity) {
                    val activity = context as MainActivity
                    activity.goToAlbumDetail(album)
                }
            }
        }
    }

    /**
     * Gets the number of albums in the list.
     */
    override fun getItemCount(): Int {
        return albums.size
    }

    /**
     * Holds the view for the album.
     */
    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val txtTitle = itemView.title_album!!
        val txtArtist = itemView.artist_album!!
        val imageAlbum = itemView.image_album!!
        val imageStatus = itemView.image_status!!

    }

    /**
     * Filters the albums in the recyclerView
     */
    fun setFilter(filteredList: ArrayList<Album>) {
        this.albums = filteredList
        notifyDataSetChanged()
    }

}


