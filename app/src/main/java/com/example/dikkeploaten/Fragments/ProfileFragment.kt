package com.example.dikkeploaten.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.dikkeploaten.R
import com.example.dikkeploaten.Services.API
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        API.shared.getUser { user ->
                name.text = user.username
                email.text = user.email
                albums_collection.text = API.shared.cache.user.plates.size.toString()
                albums_wantlist.text = API.shared.cache.user.wantList.size.toString()
            disableLoadingScreen()

        }
        API.shared.getProfileImage { imageURL ->
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(this).load(imageURL).apply(requestOptions).into(profile)
        }
        API.shared.getProfileCover { imageURL ->
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(this).load(imageURL).apply(requestOptions).into(header_cover_image)
            //disableLoadingScreen()
        }
    }

    /**
     * Disables progressBar.
     */
    private fun disableLoadingScreen() {
        if(progressBar != null) {
            progressBar.visibility = View.GONE
        }
    }

}