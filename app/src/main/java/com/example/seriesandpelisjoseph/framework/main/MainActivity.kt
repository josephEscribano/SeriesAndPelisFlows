package com.example.seriesandpelisjoseph.framework.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.data.sources.adapter.MovieAdapter
import com.example.seriesandpelisjoseph.databinding.ActivitymainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivitymainBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitymainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.findNavController()
        setSupportActionBar(binding.topBar)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.fragmentBuscarPeli
            ),binding.drawerLayout
        )

        NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)
        binding.navView.setupWithNavController(navController)




        configAppBar()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_principal,menu)
        return true
    }

    private fun configAppBar() {
        binding.topBar.setNavigationOnClickListener {
            binding.drawerLayout.open()
        }

        binding.topBar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId){
                R.id.add -> {
                    Snackbar.make(binding.root,"añadido",Snackbar.LENGTH_SHORT).show()
                    true
                }

                R.id.ver -> {
                    Snackbar.make(binding.root,"datos",Snackbar.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
    }
}