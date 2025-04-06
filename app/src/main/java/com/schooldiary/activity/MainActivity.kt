package com.schooldiary.activity

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
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
        return when (item.itemId) {
            R.id.exit -> {
                logout()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        val sharedPref = getSharedPreferences(getString(R.string.shared_pref), Context.MODE_PRIVATE)
        AlertDialog.Builder(this)
            .setTitle("Внимание!")
            .setMessage("Вы уверены, что хотите выйти?")
            .setPositiveButton("Да") { _, _ ->
                sharedPref
                    .edit()
                    .putBoolean(getString(R.string.sp_login_state), false)
                    .apply()
                val navOptions = NavOptions.Builder().setPopUpTo(R.id.mainFlow, true).build()
                navController.navigate(
                    R.id.action_global_auth_flow,
                    null,
                    navOptions
                )
                //findNavController(R.id.nav_host_fragment).navigate(R.id.action_scheduleFragment_to_loginFragment)
            }
            .setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
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
