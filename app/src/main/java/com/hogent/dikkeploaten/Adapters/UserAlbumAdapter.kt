package com.hogent.dikkeploaten.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.hogent.dikkeploaten.databinding.ListItemUserAlbumBinding
import com.hogent.dikkeploaten.models.ViewAlbumAndUserAlbums

internal class UserAlbumAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<ViewAlbumAndUserAlbums, UserAlbumViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAlbumViewHolder {
        return UserAlbumViewHolder(ListItemUserAlbumBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UserAlbumViewHolder, position: Int) {
        val album = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(album)
        }
        holder.bindData(album)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [DatabaseAlbumAndUserAlbums]
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


    class OnClickListener(val clickListener: (album: ViewAlbumAndUserAlbums) -> Unit) {
        fun onClick(album: ViewAlbumAndUserAlbums) = clickListener(album)
    }

}