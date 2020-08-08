package com.hogent.dikkeploaten.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * Base class every ViewHolder in the app should extend.
 * @param T the type of the given member.
 * @property itemView the given [View].
 */
abstract class ViewHolder<in T>(itemView: View) : RecyclerView.ViewHolder(itemView),
    LayoutContainer {

    override val containerView: View
        get() = itemView

    abstract fun bindData(data: T)
}