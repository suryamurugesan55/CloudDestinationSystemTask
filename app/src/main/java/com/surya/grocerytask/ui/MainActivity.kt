package com.surya.grocerytask.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.surya.grocerytask.R
import com.surya.grocerytask.base.BaseApplication
import com.surya.grocerytask.databinding.ActivityMainBinding
import com.surya.grocerytask.utils.SharedPref
import com.surya.grocerytask.viewmodel.ProductViewModel
import com.surya.grocerytask.viewmodel.ProductViewModelFactory
import javax.inject.Inject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var productViewModel: ProductViewModel

    @Inject
    lateinit var sharedPref: SharedPref

    @Inject
    lateinit var productViewModelFactory: ProductViewModelFactory

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_todo, R.id.navigation_pending, R.id.navigation_complete
            )
        )
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.apply {
            binding.toolbar.setTitleTextColor(Color.WHITE)
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        (application as BaseApplication).applicationComponent.inject(this)

        if (!sharedPref.isFirstTime) {
            productViewModel =
                ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]
        }

    }


}