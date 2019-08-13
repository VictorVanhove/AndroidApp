package com.example.dikkeploaten.Activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dikkeploaten.Fragments.*
import com.example.dikkeploaten.Models.Album
import com.example.dikkeploaten.Services.API
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private fun loadFragment(fragment: Fragment): Boolean {
        if(fragment != null) {
            supportFragmentManager.beginTransaction().replace(com.example.dikkeploaten.R.id.fragment_container, fragment).commit()
            return true
        }
        return false
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var fragment = Fragment()
        when (item.itemId) {
            com.example.dikkeploaten.R.id.navigation_collection -> {
                fragment = CollectionFragment()
            }
            com.example.dikkeploaten.R.id.navigation_wantlist -> {
                fragment = WantlistFragment()
            }
            com.example.dikkeploaten.R.id.navigation_search -> {
                fragment = SearchFragment()
            }
            com.example.dikkeploaten.R.id.navigation_notifications -> {
                fragment = NotificationsFragment()
            }
            com.example.dikkeploaten.R.id.navigation_profile -> {
                fragment = ProfileFragment()
            }
        }
        return loadFragment(fragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.dikkeploaten.R.layout.activity_main)

        if (API.shared.isUserLoggedIn()) {
            loadFragment(CollectionFragment())
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val navView: BottomNavigationView = findViewById(com.example.dikkeploaten.R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(this)
    }

    fun goToAlbumDetail(album: Album) {
        val frag = AlbumDetailFragment()
        frag.initiateAttributes(album)
        supportFragmentManager.beginTransaction().replace(com.example.dikkeploaten.R.id.fragment_container, frag).addToBackStack(null).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.example.dikkeploaten.R.menu.settings_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.example.dikkeploaten.R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
