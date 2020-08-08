package com.hogent.dikkeploaten.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hogent.dikkeploaten.databinding.ListItemAlbumBinding
import com.hogent.dikkeploaten.models.ViewAlbum
import com.hogent.domain.models.Album

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present
 * a [List] [Album], including computing diffs between lists.
 * @param onClickListener a lambda that handles the clicked [Album].
 */
class AlbumAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ViewAlbum, AlbumViewHolder>(DiffCallback) {

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(ListItemAlbumBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
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
    companion object DiffCallback : DiffUtil.ItemCallback<ViewAlbum>() {
        override fun areItemsTheSame(oldItem: ViewAlbum, newItem: ViewAlbum): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ViewAlbum, newItem: ViewAlbum): Boolean {
            return oldItem.albumId == newItem.albumId
        }
    }


    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Album]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Album].
     */
    class OnClickListener(val clickListener: (album: ViewAlbum) -> Unit) {
        fun onClick(album: ViewAlbum) = clickListener(album)
    }

}


