package com.hogent.dikkeploaten.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hogent.dikkeploaten.models.Album
import com.hogent.dikkeploaten.databinding.ListItemAlbumBinding

/**
 * Adapter class for each album in recyclerView.
 */
class AlbumAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Album, AlbumViewHolder>(DiffCallback) {

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(ListItemAlbumBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Binds each album in the ArrayList to a view.
     */
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(album)
        }
        holder.bindData(album)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Album]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
            return oldItem.albumId == newItem.albumId
        }
    }


    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Album]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Album]
     */
    class OnClickListener(val clickListener: (album: Album) -> Unit) {
        fun onClick(album: Album) = clickListener(album)
    }

}


