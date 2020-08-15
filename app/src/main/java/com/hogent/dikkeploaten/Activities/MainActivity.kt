package com.hogent.dikkeploaten.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hogent.dikkeploaten.R
import com.hogent.dikkeploaten.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

/**
 * This [AppCompatActivity] is the main activity of the app.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_DikkePloaten)
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
