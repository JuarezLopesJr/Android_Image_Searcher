package com.example.getimages

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager

import kotlinx.android.synthetic.main.content_main.*

class MainActivity : BasicActivity(), OnDownloadComplete, OnDataAvailable, OnRecyclerClickListener {
    private val imageRecyclerViewAdapter = ImageRecyclerViewAdapter(ArrayList())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activeToolbar(false)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addOnItemTouchListener(RecyclerItemClickListener(this, recyclerView, this))
        recyclerView.adapter = imageRecyclerViewAdapter

        /*val fetchData = FetchData(this)
        // Calling .execute because is AsyncTask
        fetchData.execute("https://api.flickr.com/services/feeds/photos_public.gne?tags=android,oreo&format=json&nojsoncallback=1")*/

        val url = createUri(
            "https://api.flickr.com/services/feeds/photos_public.gne&format=json&nojsoncallback=1",
            "android,oreo",
            "en-us",
            true
        )

        val fetchData = GetJsonData(this)
        fetchData.execute(url)

    }

    override fun onItemClick(view: View, position: Int) {
        Toast.makeText(this, "Normal tap at position $position", Toast.LENGTH_SHORT).show()
    }

    override fun onItemLongClick(view: View, position: Int) {
        val image = imageRecyclerViewAdapter.getImages(position)

        if (image == null) {
            val intent = Intent(this, ImageDetailsActivity::class.java)
//            intent.putExtra(IMAGE_TRANSFER, image)
            startActivity(intent)
        }
    }

    private fun createUri(baseUrl: String, searchCriteria: String, lang: String, matchAll: Boolean): String {
        return Uri.parse(baseUrl)
            .buildUpon()
            .appendQueryParameter("tags", searchCriteria)
            .appendQueryParameter("tagmode", if (matchAll) "ALL" else "ANY")
            .appendQueryParameter("lang", lang)
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .build()
            .toString()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_search -> {
                startActivity(Intent(this, SearchActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDownloadComplete(data: String, status: DownloadStatus) {
        if (status == DownloadStatus.SUCCESS) {
            val getWebJsonData = GetJsonData(this)
            getWebJsonData.execute(data)
        } else {
            TODO()
        }
    }

    override fun onDataAvailable(data: List<ImagePayload>) {
        imageRecyclerViewAdapter.loadNewData(data)
    }

    override fun onError(error: Exception) {
        TODO()
    }

    override fun onResume() {
        super.onResume()
    }
}
