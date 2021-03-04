package com.scandrug.scandrug.presentation.home.delivery

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.scandrug.scandrug.BuildConfig
import com.scandrug.scandrug.R
import com.scandrug.scandrug.base.BaseApplication
import com.scandrug.scandrug.data.local.AppPreferences
import com.scandrug.scandrug.data.local.CacheInMemory
import com.scandrug.scandrug.presentation.authentication.AuthenticationActivity
import kotlinx.android.synthetic.main.activity_main.*

class DeliveryActivity : AppCompatActivity() {
    lateinit var drawerLayout: DrawerLayout
    private var sharedPreferences =
        BaseApplication.instance.getSharedPreferences(BuildConfig.PREF_NAME, Context.MODE_PRIVATE)
    private lateinit var appPreferences: AppPreferences
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navlistner: NavController.OnDestinationChangedListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery)
        drawerLayout = findViewById(R.id.drawer_layout)

        // Set the toolbar
        setSupportActionBar(activity_main_toolbar)
        activity_main_toolbar.title = "Your App Name"
        appPreferences = AppPreferences(sharedPreferences)

        navController = findNavController(R.id.nav_host_fragment)
        nav_view.setupWithNavController(navController) //
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        nav_view.setNavigationItemSelectedListener { menuItem ->
            val id: Int = menuItem.itemId
            if (id == R.id.nav_second_fragment) {
                drawerLayout.closeDrawer(GravityCompat.START)
                appPreferences.removeAccessToken()
                val intent = Intent(this, AuthenticationActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                CacheInMemory.clearProfileData()
                startActivity(intent)
                finish()
            }
            true
        }

        navlistner = NavController.OnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.deliveryOrdersFragment){
                supportActionBar?.apply {
                    setHomeButtonEnabled(true)
                    setDisplayHomeAsUpEnabled(true)
                    setHomeAsUpIndicator(R.drawable.ic_menu)
                    setDisplayShowTitleEnabled(false)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cart_item -> {
                navController.popBackStack(R.id.cartFragment, true)
                navController.navigate(R.id.cartFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}