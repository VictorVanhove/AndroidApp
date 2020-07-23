package com.hogent.dikkeploaten.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hogent.dikkeploaten.database.DatabaseAlbum
import com.hogent.dikkeploaten.databinding.LayoutAlbumItemBinding

/**
 * Adapter class for each album in recyclerView.
 */
class AlbumAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<DatabaseAlbum, AlbumAdapter.AlbumPropertyViewHolder>(DiffCallback) {

    /**
     * The MarsPropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [MarsProperty] information.
     */
    class AlbumPropertyViewHolder(private var binding: LayoutAlbumItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(albumProperty: DatabaseAlbum) {
            binding.album = albumProperty
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }

    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [MarsProperty]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<DatabaseAlbum>() {
        override fun areItemsTheSame(oldItem: DatabaseAlbum, newItem: DatabaseAlbum): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DatabaseAlbum, newItem: DatabaseAlbum): Boolean {
            return oldItem.albumId == newItem.albumId
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumPropertyViewHolder {
        return AlbumPropertyViewHolder(LayoutAlbumItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Binds each album in the ArrayList to a view.
     */
    override fun onBindViewHolder(holder: AlbumPropertyViewHolder, position: Int) {
        val albumProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(albumProperty)
        }
        holder.bind(albumProperty)
//        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
//        Glide.with(context).load(album.thumb).apply(requestOptions).into(holder.imageAlbum)
//
//        //Adds status image if album belongs to collection/wantlist
//        if (showStatus) {
//            when {
//                API.shared.cache.user.plates.any { userAlbum -> userAlbum.albumID == album.id } -> Glide.with(context).load(
//                    R.mipmap.ic_in_collection
//                ).apply(requestOptions).into(holder.imageStatus)
//                API.shared.cache.user.wantList.any { userAlbum -> userAlbum.albumID == album.id } -> Glide.with(context).load(
//                    R.mipmap.ic_in_wantlist
//                ).apply(requestOptions).into(holder.imageStatus)
//                else -> Glide.with(context).load(R.drawable.ic_not_in_collection).apply(requestOptions).into(holder.imageStatus)
//            }
//        }
//
//        holder.itemView.setOnClickListener {
//            run {
//                if (context is MainActivity) {
//                    val activity = context as MainActivity
//                    //activity.goToAlbumDetail(album)
//                }
//            }
//        }
    }

    /**
     * Gets the number of albums in the list.
     */
    //override fun getItemCount() = albums.size

    /**
     * Filters the albums in the recyclerView
     */
    fun setFilter(filteredList: ArrayList<DatabaseAlbum>) {
        //this.albums = filteredList
        notifyDataSetChanged()
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [MarsProperty]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [MarsProperty]
     */
    class OnClickListener(val clickListener: (albumProperty: DatabaseAlbum) -> Unit) {
        fun onClick(albumProperty: DatabaseAlbum) = clickListener(albumProperty)
    }

}


