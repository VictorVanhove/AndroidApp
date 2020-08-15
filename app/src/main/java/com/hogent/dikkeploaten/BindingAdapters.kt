package com.hogent.dikkeploaten

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.hogent.dikkeploaten.viewmodels.ApiStatus

/**
 * When the included condition is true, hide the [View], otherwise show it.
 * @param view The specific [View].
 * @param isGone The [Boolean] which returns the condition of the current [View].
 */
@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 * @param imgView The [ImageView] in which the image gets loaded.
 * @param imgUrl The [String] URL of the image.
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
            )
            .into(imgView)
    }
}

/**
 * This binding adapter returns the [ApiStatus] of the network request for the [ProgressBar].
 * When the request is loading, the [ProgressBar] is set visible. When the request gets error
 * or is finished, the [ProgressBar] is * set to invisible.
 * @param progressBar The [ProgressBar] when albums are loading.
 * @param status The [ApiStatus] of the network request.
 */
@BindingAdapter("statusForProgressBar")
fun bindStatusForProgressBar(progressBar: ProgressBar, status: ApiStatus?) {
    progressBar.visibility = if (status == ApiStatus.LOADING) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

/**
 * This binding adapter returns the [ApiStatus] of the network request for the [View].
 * When the request gets an error, the [View] is getting showed. When the request is still
 * loading or finished (thus no error), the [View] must be invisible.
 * @param view The specific [View].
 * @param status The [ApiStatus] of the network request.
 */
@BindingAdapter("statusForButton")
fun bindStatusForButton(view: View, status: ApiStatus?) {
    view.visibility = if (status == ApiStatus.ERROR) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
