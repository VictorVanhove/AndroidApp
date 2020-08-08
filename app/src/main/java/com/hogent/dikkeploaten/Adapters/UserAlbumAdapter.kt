package com.hogent.dikkeploaten.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hogent.dikkeploaten.databinding.ListItemUserAlbumBinding
import com.hogent.dikkeploaten.models.ViewAlbumAndUserAlbums

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present
 * a [List] [UserAlbum], including computing diffs between lists.
 * @param onClickListener a lambda that handles the clicked [UserAlbum].
 */
internal class UserAlbumAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ViewAlbumAndUserAlbums, UserAlbumViewHolder>(DiffCallback) {

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAlbumViewHolder {
        return UserAlbumViewHolder(ListItemUserAlbumBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: UserAlbumViewHolder, position: Int) {
        val album = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(album)
        }
        holder.bindData(album)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [ViewAlbumAndUserAlbums]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<ViewAlbumAndUserAlbums>() {
        override fun areItemsTheSame(
            oldItem: ViewAlbumAndUserAlbums,
            newItem: ViewAlbumAndUserAlbums
        ): Boolean {
            return oldItem.album.albumId == newItem.album.albumId
        }

        override fun areContentsTheSame(
            oldItem: ViewAlbumAndUserAlbums,
            newItem: ViewAlbumAndUserAlbums
        ): Boolean {
            return oldItem.album == newItem.album
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [UserAlbum]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [UserAlbum].
     */
    class OnClickListener(val clickListener: (album: ViewAlbumAndUserAlbums) -> Unit) {
        fun onClick(album: ViewAlbumAndUserAlbums) = clickListener(album)
    }

}