package com.hogent.dikkeploaten

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hogent.dikkeploaten.adapters.AlbumAdapter
import com.hogent.dikkeploaten.models.ViewAlbum
import com.hogent.dikkeploaten.viewmodels.ApiStatus


@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

/**
 * When there is no Mars property data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<ViewAlbum>?) {
    val adapter = recyclerView.adapter as AlbumAdapter
    adapter.submitList(data)
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.loading_animation))
            .into(imgView)
    }
}

/**
 * This binding adapter displays the [MarsApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("statusForImage")
fun bindStatusForImage(statusImageView: ImageView, status: ApiStatus?) {
    when (status) {
        ApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        ApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        ApiStatus.DONE -> {
            statusImageView.visibility = View.VISIBLE
        }
    }
}

@BindingAdapter("statusForProgressBar")
fun bindStatusForProgressBar(progressBar: ProgressBar, status: ApiStatus?) {
    progressBar.visibility = if (status == ApiStatus.LOADING) {
        View.VISIBLE
    } else {
        View.INVISIBLE
    }
}

@BindingAdapter("statusForButton")
fun bindStatusForButton(view: View, status: ApiStatus?) {
    if (status == ApiStatus.ERROR) {
        view.visibility = View.VISIBLE
    }
    if (status == ApiStatus.LOADING) {
        view.visibility = View.GONE
    }
}


