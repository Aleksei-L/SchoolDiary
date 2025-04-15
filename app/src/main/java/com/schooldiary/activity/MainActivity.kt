package com.schooldiary.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.schooldiary.R
import com.schooldiary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.loginFragment, R.id.scheduleFragment))

        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        binding.bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, _, _ ->
            changeBottomBarAndToolbarMenuVisibility()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        if (this::navController.isInitialized)
            changeBottomBarAndToolbarMenuVisibility()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    private fun changeBottomBarAndToolbarMenuVisibility() {
        if (navController.currentDestination?.id == R.id.loginFragment) {
            binding.bottomNav.visibility = View.GONE
            binding.toolbar.visibility = View.GONE
        } else {
            binding.bottomNav.visibility = View.VISIBLE
            binding.toolbar.visibility = View.VISIBLE
        }
    }
}
