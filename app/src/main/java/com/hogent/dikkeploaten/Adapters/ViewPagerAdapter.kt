package com.hogent.dikkeploaten.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hogent.dikkeploaten.fragments.CollectionFragment
import com.hogent.dikkeploaten.fragments.SearchFragment
import com.hogent.dikkeploaten.fragments.WantlistFragment

const val COLLECTION_PAGE_INDEX = 0
const val SEARCH_PAGE_INDEX = 1
const val WANTLIST_PAGE_INDEX = 2

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        COLLECTION_PAGE_INDEX to { CollectionFragment() },
        SEARCH_PAGE_INDEX to { SearchFragment() },
        WANTLIST_PAGE_INDEX to { WantlistFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}