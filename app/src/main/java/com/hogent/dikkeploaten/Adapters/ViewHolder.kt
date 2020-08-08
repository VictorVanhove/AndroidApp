package com.hogent.dikkeploaten.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Base class used in [AlbumViewHolder] and [UserAlbumViewHolder], gets extended in all view holders.
 *
 * @param T the type of the given member
 * @property itemView the [View] of the given item
 */
abstract class ViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView),
    LayoutContainer {

    override val containerView: View
        get() = itemView

    abstract fun bindData(data: T)
}