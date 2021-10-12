package ru.gb.moviesearcher.ui.main.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

import com.google.android.material.navigation.NavigationView
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.databinding.ActivityMainBinding
import ru.gb.moviesearcher.ui.main.view.googlemaps.MapsFragment
import ru.gb.moviesearcher.ui.main.view.history.HistoryFragment
import ru.gb.moviesearcher.ui.main.view.main.MainFragment

const val CHILD_MODE= "Child_Mode"
const val CHILD_MODE_KEY ="KEYChildModeKey"

class MainActivity : AppCompatActivity() {


    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {

//        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView();
        initNotificationChannel();
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }


    }


    private fun initNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val importance: Int = NotificationManager.IMPORTANCE_LOW
            val channel: NotificationChannel = NotificationChannel("1","name", importance)
            notificationManager.createNotificationChannel(channel)
        }

    }

    private fun initView() {
        val toolbar: Toolbar = initToolbar()
        initDrawer(toolbar)

    }

    private fun initToolbar(): Toolbar {
        val toolbar: Toolbar = binding.appBarMain.toolbar
        setSupportActionBar(toolbar)
        return toolbar
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val search = menu!!.findItem(R.id.action_search)
        val searchText: SearchView = search.actionView as SearchView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(applicationContext, query, Toast.LENGTH_SHORT).show();
                return true

            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_settings -> {
//                Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, SettingsFragment())
                        .addToBackStack("")
                        .commit()
                }
                return true
            }

            R.id.action_find_on_map ->{
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, MapsFragment())
                        .addToBackStack("")
                        .commit()
                }
            }
            R.id.action_contacts ->{
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, ContactsFragment())
                        .addToBackStack("")
                        .commit()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initDrawer(toolbar: Toolbar) {
        val drawer: DrawerLayout = binding.drawerLayout
        val toggle: ActionBarDrawerToggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = binding.navView
        navigationView.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                var id = item.itemId
                if (navigateFragment(id)) {
                    drawer.closeDrawer(GravityCompat.START)
                    return true
                }
                return false
            }

        })

    }

    private fun navigateFragment(id: Int): Boolean {
        when (id) {
            R.id.nav_history -> {
                supportFragmentManager.apply {
                    beginTransaction()
                        .replace(R.id.container, HistoryFragment())
                        .addToBackStack("")
                        .commit()
                }
                return true
            }
            R.id.nav_favourite -> {
                Toast.makeText(applicationContext, "Favourites has opened", Toast.LENGTH_SHORT)
                    .show()
                return true
            }

            R.id.nav_trending -> {
                Toast.makeText(applicationContext, "Trending has opened", Toast.LENGTH_SHORT).show()
                return true
            }

        }
        return true
    }



    override fun onDestroy() {
        super.onDestroy()
    }


}