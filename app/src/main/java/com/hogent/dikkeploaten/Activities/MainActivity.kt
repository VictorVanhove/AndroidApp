package com.hogent.dikkeploaten.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hogent.dikkeploaten.R

/**
 * Activity class for the main part of the app.
 */
class MainActivity : AppCompatActivity() {

    /**
     * Loads the fragment equal to the parameter and updates the container.
     */
//    private fun loadFragment(fragment: Fragment): Boolean {
//        if (fragment != null) {
//            supportFragmentManager.beginTransaction().replace(com.hogent.dikkeploaten.R.id.fragment_container, fragment)
//                .commit()
//            return true
//        }
//        return false
//    }

    /**
     * Handles actions when an item in the bottom navigation menu was clicked.
     */
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        var fragment = Fragment()
//        when (item.itemId) {
//            com.hogent.dikkeploaten.R.id.navigation_collection -> {
//                fragment = CollectionFragment()
//            }
//            com.hogent.dikkeploaten.R.id.navigation_wantlist -> {
//                fragment = WantlistFragment()
//            }
//            com.hogent.dikkeploaten.R.id.navigation_search -> {
//                fragment = SearchFragment()
//            }
//            com.hogent.dikkeploaten.R.id.navigation_notifications -> {
//                fragment = NotificationsFragment()
//            }
//            com.hogent.dikkeploaten.R.id.navigation_profile -> {
//                fragment = ProfileFragment()
//            }
//        }
//        return loadFragment(fragment)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (API.shared.isUserLoggedIn()) {
////            loadFragment(CollectionFragment())
////        } else {
////            val intent = Intent(this, LoginActivity::class.java)
////            startActivity(intent)
////            finish()
////        }
////
////        val navView: BottomNavigationView = findViewById(com.hogent.dikkeploaten.R.id.nav_view)
////
////        navView.setOnNavigationItemSelectedListener(this)

        setUpNavigation()
    }

    private fun setUpNavigation() {
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(
            bottomNavigationView,
            navHostFragment!!.navController
        )
    }

    /**
     * Initializes clicked album and loads the AlbumDetailFragment.
     */
//    fun goToAlbumDetail(album: Album) {
//        val frag = AlbumDetailFragment()
//        frag.initiateAlbum(album)
//        supportFragmentManager.beginTransaction().replace(com.hogent.dikkeploaten.R.id.fragment_container, frag)
//            .addToBackStack(null).commit()
//    }

    /**
     * Inflates the settings menu item into the actionbar.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(com.hogent.dikkeploaten.R.menu.settings_menu, menu)
        return true
    }

    /**
     * Handles actions for settings menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            com.hogent.dikkeploaten.R.id.action_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

}
