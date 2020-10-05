package com.eneskayiklik.comicreader.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.eneskayiklik.comicreader.R
import com.eneskayiklik.comicreader.utils.Functions.makeInvisible
import com.eneskayiklik.comicreader.utils.Functions.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
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
                R.id.splashFragment -> bottomNavigationView.makeInvisible()
                R.id.readBookFragment -> bottomNavigationView.makeInvisible()
                else -> bottomNavigationView.makeVisible()
            }
        }

        bottomNavigationView.setOnNavigationItemReselectedListener {
            bottomNavigationView.willNotDraw()
        }
    }
}