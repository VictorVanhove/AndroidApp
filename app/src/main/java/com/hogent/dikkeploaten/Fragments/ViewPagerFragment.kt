package com.hogent.dikkeploaten.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.hogent.dikkeploaten.R
import com.hogent.dikkeploaten.adapters.ViewPagerAdapter
import com.hogent.dikkeploaten.adapters.COLLECTION_PAGE_INDEX
import com.hogent.dikkeploaten.adapters.SEARCH_PAGE_INDEX
import com.hogent.dikkeploaten.adapters.WANTLIST_PAGE_INDEX
import com.hogent.dikkeploaten.databinding.FragmentViewPagerBinding
import com.hogent.domain.models.Album

/**
 * This [Fragment] represents the view pager page in which the [Fragment]s getting loaded.
 */
class ViewPagerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabsMenu
        val viewPager = binding.viewPager

        viewPager.adapter = ViewPagerAdapter(this)

        // Set the icon and text for each tab
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabTitle(position)
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            COLLECTION_PAGE_INDEX -> R.drawable.collection_tab_selector
            SEARCH_PAGE_INDEX -> R.drawable.search_tab_selector
            WANTLIST_PAGE_INDEX -> R.drawable.wantlist_tab_selector
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            COLLECTION_PAGE_INDEX -> getString(R.string.title_collection)
            SEARCH_PAGE_INDEX -> getString(R.string.title_search)
            WANTLIST_PAGE_INDEX -> getString(R.string.title_wantlist)
            else -> null
        }
    }
}