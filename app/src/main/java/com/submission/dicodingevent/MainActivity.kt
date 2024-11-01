package com.submission.dicodingevent

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.submission.dicodingevent.databinding.ActivityMainBinding
import com.submission.dicodingevent.ui.datastorepref.MainViewModel
import com.submission.dicodingevent.ui.datastorepref.Settingpref
import com.submission.dicodingevent.ui.datastorepref.VmFactory
import com.submission.dicodingevent.ui.datastorepref.dataStore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels {
        VmFactory(Settingpref.getInstance(applicationContext.dataStore))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.getThemeSet().observe(this, Observer { isDarkModeActive ->
            val modeapps = if (isDarkModeActive) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(modeapps)
        })

        val navView: BottomNavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_notifications,
                R.id.navigation_favorite,
                R.id.navigation_dashboard,
                R.id.navigation_setting,
            )
        )

        setSupportActionBar(binding.toolbar)
        binding.toolbar.visibility = View.GONE
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun switchDarkTheme(isChecked: Boolean) {
        mainViewModel.saveThemeSet(isChecked)
    }
}
