package com.hogent.dikkeploaten.adapters

import com.hogent.dikkeploaten.databinding.ListItemUserAlbumBinding
import com.hogent.dikkeploaten.models.ViewAlbumAndUserAlbums
import com.hogent.dikkeploaten.viewmodels.AlbumAndUserAlbumsViewModel

/**
 * The UserAlbumViewHolder constructor takes the binding variable from the associated
 * GridViewItem, which nicely gives it access to the full [ViewAlbumAndUserAlbums] information.
 */
internal class UserAlbumViewHolder(private var binding: ListItemUserAlbumBinding) :
    ViewHolder<ViewAlbumAndUserAlbums>(binding.root) {

    override fun bindData(data: ViewAlbumAndUserAlbums) {
        with(binding) {
            viewModel = AlbumAndUserAlbumsViewModel(data)
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            executePendingBindings()
        }
    }

}