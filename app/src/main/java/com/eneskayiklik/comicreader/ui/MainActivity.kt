package com.eneskayiklik.comicreader.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eneskayiklik.comicreader.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavWithFragment()
    }

    private fun setupBottomNavWithFragment() {
        bottomNavigationView.setupWithNavController(fragmentHost.findNavController())
        val navController = findNavController(R.id.fragmentHost)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.bookShelfFragment -> bottomNavigationView.visibility = View.VISIBLE
            }
        }
    }
}