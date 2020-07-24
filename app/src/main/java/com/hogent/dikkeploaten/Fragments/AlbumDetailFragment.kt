package com.hogent.dikkeploaten.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.hogent.dikkeploaten.R
import com.hogent.dikkeploaten.database.DatabaseAlbum
import com.hogent.dikkeploaten.databinding.FragmentAlbumInfoBinding
import com.hogent.dikkeploaten.network.Album
import com.hogent.dikkeploaten.utilities.InjectorUtils
import com.hogent.dikkeploaten.viewmodels.AlbumDetailViewModel

/**
 * Fragment class for information page of each album.
 */
class AlbumDetailFragment : Fragment() {

    private lateinit var album: Album
    private val args: AlbumDetailFragmentArgs by navArgs()

    private val albumDetailViewModel: AlbumDetailViewModel by viewModels {
        InjectorUtils.provideAlbumDetailViewModelFactory(requireActivity(), args.selectedProperty)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAlbumInfoBinding>(
            inflater, R.layout.fragment_album_info, container, false
        ).apply {
            viewModel = albumDetailViewModel
            lifecycleOwner = viewLifecycleOwner
            callback = object : Callback {
                override fun addToCollection(plant: DatabaseAlbum?) {
                    plant?.let {
                        hideAppBarFab(fab)
                        albumDetailViewModel.addAlbumToCollection()
                        Snackbar.make(root, "Added album to collection", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }

                override fun addToWantlist(plant: DatabaseAlbum?) {
                    plant?.let {
                        hideAppBarFab(fab2)
                        albumDetailViewModel.addAlbumToWantlist()
                        Snackbar.make(root, "Added album to wantlist", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }

            var isToolbarShown = false

            // scroll change listener begins at Y = 0 when image is fully collapsed
            albumDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // User scrolled past image to height of toolbar and the title text is
                    // underneath the toolbar, so the toolbar should be shown.
                    val shouldShowToolbar = scrollY > toolbar.height

                    // The new state of the toolbar differs from the previous state; update
                    // appbar and toolbar attributes.
                    if (isToolbarShown != shouldShowToolbar) {
                        isToolbarShown = shouldShowToolbar

                        // Use shadow animator to add elevation if toolbar is shown
                        appbar.isActivated = shouldShowToolbar

                        // Show the plant name if toolbar is shown
                        toolbarLayout.isTitleEnabled = shouldShowToolbar
                    }
                }
            )

            toolbar.setNavigationOnClickListener { view ->
                view.findNavController().navigateUp()
            }

        }

        return binding.root
    }

    // FloatingActionButtons anchored to AppBarLayouts have their visibility controlled by the scroll position.
    // We want to turn this behavior off to hide the FAB when it is clicked.
    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }

    interface Callback {
        fun addToCollection(plant: DatabaseAlbum?)
        fun addToWantlist(plant: DatabaseAlbum?)
    }
}