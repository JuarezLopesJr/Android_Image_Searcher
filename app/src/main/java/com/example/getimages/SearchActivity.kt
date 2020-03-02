package com.example.getimages

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import java.util.prefs.PreferenceChangeEvent
import java.util.prefs.PreferenceChangeListener
import java.util.prefs.Preferences
import java.util.prefs.PreferencesFactory

class SearchActivity : BasicActivity() {

    private var searchView: SearchView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        activeToolbar(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        val searchableInfo = searchManager.getSearchableInfo(componentName)
        searchView?.setSearchableInfo(searchableInfo)
        // if true will display the magnifying glass, if false will display the search input
        searchView?.isIconified = false

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {

                searchView?.clearFocus()
                finish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return true
    }
}
