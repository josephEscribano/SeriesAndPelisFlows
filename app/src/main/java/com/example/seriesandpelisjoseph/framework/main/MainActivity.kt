package com.example.seriesandpelisjoseph.framework.main

import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.seriesandpelisjoseph.R
import com.example.seriesandpelisjoseph.databinding.ActivitymainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var binding: ActivitymainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitymainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
//        navController = navHostFragment.findNavController()
//        setSupportActionBar(binding.mainBar)
//        appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.fragmentBuscarPelis
//            ), binding.mainActivity
//        )
//
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
//        binding.navView.setupWithNavController(navController)




//        configAppBar()


    }


//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_principal, menu)
//        return true
//    }

//    private fun configAppBar() {
//        binding.mainBar.setNavigationOnClickListener {
//            binding.mainActivity.open()
//        }
//
//
//
//    }
}