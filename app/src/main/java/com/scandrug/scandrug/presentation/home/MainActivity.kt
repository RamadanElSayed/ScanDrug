package com.scandrug.scandrug.presentation.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.databinding.adapters.TimePickerBindingAdapter
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.scandrug.scandrug.R
import com.scandrug.scandrug.presentation.home.drawer.ClickListener
import com.scandrug.scandrug.data.localmodel.NavigationItemModel
import com.scandrug.scandrug.presentation.home.drawer.NavigationRVAdapter
import com.scandrug.scandrug.presentation.home.drawer.RecyclerTouchListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_scan_drug.*
import kotlinx.coroutines.newFixedThreadPoolContext

class MainActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navlistner: NavController.OnDestinationChangedListener
    private var items = arrayListOf(
        NavigationItemModel(R.drawable.ic_baseline_check_circle_24, "Completed requests"),
        NavigationItemModel(R.drawable.ic_baseline_logout, "Logout"),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout = findViewById(R.id.drawer_layout)

        // Set the toolbar
        setSupportActionBar(activity_main_toolbar)






        navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController) //
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)



        // Set Header Image
//        navigation_header_img.setImageResource(R.drawable.scan_drug)
//        activity_main_toolbar.setNavigationOnClickListener {
//            drawerLayout.open()
//        }

//        nav_view.setNavigationItemSelectedListener { menuItem ->
//            // Handle menu item selected
//            menuItem.isChecked = true
//            drawerLayout.close()
//            true
//        }

         navlistner = NavController.OnDestinationChangedListener { controller, destination, arguments ->

            if (destination.id == R.id.homeFragment){
                supportActionBar?.apply {
                    setHomeButtonEnabled(true)
                    setDisplayHomeAsUpEnabled(true)
                    setHomeAsUpIndicator(R.drawable.ic_menu)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        navController.addOnDestinationChangedListener(navlistner)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

    }
}