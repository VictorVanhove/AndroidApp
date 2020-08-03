package com.hogent.dikkeploaten.adapters

import com.hogent.database.models.AlbumAndUserAlbums
import com.hogent.dikkeploaten.databinding.ListItemUserAlbumBinding
import com.hogent.dikkeploaten.viewmodels.AlbumAndUserAlbumsViewModel

/**
 * The UserAlbumViewHolder constructor takes the binding variable from the associated
 * GridViewItem, which nicely gives it access to the full [AlbumAndUserAlbums] information.
 */
class UserAlbumViewHolder(private var binding: ListItemUserAlbumBinding) :
    ViewHolder<AlbumAndUserAlbums>(binding.root) {

    override fun bindData(data: AlbumAndUserAlbums) {
        with(binding) {
            viewModel = AlbumAndUserAlbumsViewModel(data)
            executePendingBindings()
        }
    }

}