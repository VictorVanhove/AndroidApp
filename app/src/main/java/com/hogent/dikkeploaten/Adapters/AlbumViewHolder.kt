package com.hogent.dikkeploaten.adapters

import com.hogent.dikkeploaten.databinding.ListItemAlbumBinding
import com.hogent.dikkeploaten.models.ViewAlbum

/**
 * The AlbumViewHolder constructor takes the binding variable from the associated
 * GridViewItem, which nicely gives it access to the full [ViewAlbum] information.
 */
class AlbumViewHolder(private var binding: ListItemAlbumBinding) :
    ViewHolder<ViewAlbum>(binding.root) {

    override fun bindData(data: ViewAlbum) {
        binding.album = data
        // This is important, because it forces the data binding to execute immediately,
        // which allows the RecyclerView to make the correct view size measurements
        binding.executePendingBindings()
    }

}