package com.hogent.dikkeploaten.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.hogent.dikkeploaten.databinding.ListItemAlbumBinding
import com.hogent.dikkeploaten.models.Album

/**
 * The AlbumViewHolder constructor takes the binding variable from the associated
 * GridViewItem, which nicely gives it access to the full [Album] information.
 */
class AlbumViewHolder(private var binding: ListItemAlbumBinding) :
    ViewHolder<Album>(binding.root) {

    override fun bindData(data: Album) {
        binding.album = data
        // This is important, because it forces the data binding to execute immediately,
        // which allows the RecyclerView to make the correct view size measurements
        binding.executePendingBindings()
    }

}