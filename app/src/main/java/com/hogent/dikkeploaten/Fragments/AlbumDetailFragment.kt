package com.hogent.dikkeploaten.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
import com.hogent.dikkeploaten.databinding.FragmentAlbumInfoBinding
import com.hogent.dikkeploaten.utilities.InjectorUtils
import com.hogent.dikkeploaten.viewmodels.AlbumDetailViewModel

/**
 * Fragment class for information page of each album.
 */
class AlbumDetailFragment : Fragment() {

    private var isFabExpanded = false
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
                override fun addToCollection(plant: com.hogent.database.DatabaseAlbum?) {
                    plant?.let {
                        hideAppBarFab(fab)
                        albumDetailViewModel.addAlbumToCollection()
                        Snackbar.make(root, "Added album to collection", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }

                override fun addToWantlist(plant: com.hogent.database.DatabaseAlbum?) {
                    plant?.let {
                        hideAppBarFab(fab)
                        albumDetailViewModel.addAlbumToWantlist()
                        Snackbar.make(root, "Added album to wantlist", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }

            // Checks if fab is pressed and in which state it is, then load the right animation
            fab.setOnClickListener {
                if (isFabExpanded) {
                    fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.reverse_rotate_button))
                    fab1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                    fab2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                    isFabExpanded = false
                } else {
                    dial.visibility = View.VISIBLE
                    fab.startAnimation(AnimationUtils.loadAnimation(context, R.anim.rotate_button))
                    fab1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
                    fab2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_in))
                    isFabExpanded = true
                }
            }

            var isToolbarShown = false

            // scroll change listener begins at Y = 0 when image is fully collapsed
            albumDetailScrollview.setOnScrollChangeListener(
                NestedScrollView.OnScrollChangeListener { _, _, scrollY, _, _ ->

                    // Makes sure that the option fabs (if expanded) are removed as well when view gets scrolled
                    if (isFabExpanded) {
                        fab1.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                        fab2.startAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_out))
                        isFabExpanded = false
                    }

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
        isFabExpanded = false
        fab.hide()
    }

    interface Callback {
        fun addToCollection(plant: com.hogent.database.DatabaseAlbum?)
        fun addToWantlist(plant: com.hogent.database.DatabaseAlbum?)
    }
}