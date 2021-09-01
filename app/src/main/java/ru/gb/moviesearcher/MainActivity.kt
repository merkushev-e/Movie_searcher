package ru.gb.moviesearcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar

import ru.gb.moviesearcher.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_content)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
        initToolbar();

    }

    private fun initToolbar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
}

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main, menu)
        val search: MenuItem = menu!!.findItem(R.id.action_search)//разобраться с !! и ?

        val searchText: SearchView =  search.actionView as SearchView
        searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show();
                return true

            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })

        return true
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id: Int = item.itemId

        when (id){
            R.id.action_settings -> {
                Toast.makeText(this@MainActivity,"Settings", Toast.LENGTH_SHORT)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}