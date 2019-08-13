package com.example.dikkeploaten.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dikkeploaten.Fragments.SettingsFragment
import com.example.dikkeploaten.R

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.settings_container,
                SettingsFragment()
            )
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}