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
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
    private lateinit var adapter: NavigationRVAdapter
    private lateinit var navController: NavController

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

        // Setup Recyclerview's Layout
        navigation_rv.layoutManager = LinearLayoutManager(this)
        navigation_rv.setHasFixedSize(true)

        // Add Item Touch Listener
        navigation_rv.addOnItemTouchListener(RecyclerTouchListener(this, object : ClickListener {
            override fun onClick(view: View, position: Int) {
                when (position) {
                    0 -> {
                        // # Home Fragment
//                        val bundle = Bundle()
//                        bundle.putString("fragmentName", "Home Fragment")
//                        val homeFragment = DemoFragment()
//                        homeFragment.arguments = bundle
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.activity_main_content_id, homeFragment).commit()
                    }
                    1 -> {
                        // # Music Fragment
//                        val bundle = Bundle()
//                        bundle.putString("fragmentName", "Music Fragment")
//                        val musicFragment = DemoFragment()
//                        musicFragment.arguments = bundle
//                        supportFragmentManager.beginTransaction()
//                            .replace(R.id.activity_main_content_id, musicFragment).commit()
                    }

                }
                // Don't highlight the 'Profile' and 'Like us on Facebook' item row
                if (position != 6 && position != 4) {
                    updateAdapter(position)
                }
                Handler().postDelayed({
                    drawerLayout.closeDrawer(GravityCompat.START)
                }, 200)
            }
        }))

        // Update Adapter with item data and highlight the default menu item ('Home' Fragment)
        updateAdapter(0)

        // Set 'Home' as the default fragment when the app starts
//        val bundle = Bundle()
//        bundle.putString("fragmentName", "Home Fragment")
//        val homeFragment = ScanDrugFragment()
//        homeFragment.arguments = bundle
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.activity_main_content_id, homeFragment).commit()


        // Close the soft keyboard when you open or close the Drawer
        val toggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(this, drawerLayout, activity_main_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            override fun onDrawerClosed(drawerView: View) {
                // Triggered once the drawer closes
                super.onDrawerClosed(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }

            override fun onDrawerOpened(drawerView: View) {
                // Triggered once the drawer opens
                super.onDrawerOpened(drawerView)
                try {
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()
        navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph,drawer_layout)
        activity_main_toolbar.setupWithNavController(navController,appBarConfiguration)
//        supportActionBar?.apply {
//            setHomeButtonEnabled(true)
//            setDisplayHomeAsUpEnabled(true)
//            setHomeAsUpIndicator(R.drawable.ic_menu)
//        }


        // Set Header Image
        navigation_header_img.setImageResource(R.drawable.scan_drug)

        // Set background of Drawer
        navigation_layout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.cart_menu,menu)
        return true
    }

    private fun updateAdapter(highlightItemPos: Int) {
        adapter = NavigationRVAdapter(items, highlightItemPos)
        navigation_rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Checking for fragment count on back stack
            if (supportFragmentManager.backStackEntryCount > 0) {
                // Go to the previous fragment
                supportFragmentManager.popBackStack()
            } else {
                // Exit the app
                super.onBackPressed()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navController.let {
            it.addOnDestinationChangedListener { controller, destination, arguments ->

//                if (destination.id != R.id.homeFragment){
//                    activity_main_toolbar.menu.getItem(R.id.cart_item).setVisible(false)
//                }else{
////                    activity_main_toolbar.menu.getItem(R.id.cart_item).setVisible(true)
//                }
            }
        }

    }


    override fun onActivityReenter(resultCode: Int, data: Intent?) {

    }


}