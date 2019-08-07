package com.example.dikkeploaten.Activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dikkeploaten.Fragments.*
import com.example.dikkeploaten.Models.Album
import com.example.dikkeploaten.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private fun loadFragment(fragment: Fragment): Boolean {
        if(fragment != null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment = Fragment()
        when (item.itemId) {
            R.id.navigation_collection -> {
                fragment = CollectionFragment()
            }
            R.id.navigation_wantlist -> {
                fragment = WantlistFragment()
            }
            R.id.navigation_search -> {
                fragment = SearchFragment()
            }
            R.id.navigation_notifications -> {
                fragment = NotificationsFragment()
            }
            R.id.navigation_profile -> {
                fragment = ProfileFragment()
            }
        }
        return loadFragment(fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(this)

        loadFragment(CollectionFragment())
    }

    fun goToAlbumDetail(album: Album) {
        val frag = AlbumDetailFragment()
        frag.initiateAttributes(album)
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, frag).addToBackStack(null).commit()
    }

}
