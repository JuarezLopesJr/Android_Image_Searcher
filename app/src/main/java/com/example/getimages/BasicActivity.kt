package com.example.getimages

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

internal const val IMAGE_QUERY = "IMAGE_QUERY"
internal const val IMAGE_TRANSFER = "IMAGE_TRANSFER"

open class BasicActivity : AppCompatActivity() {

    internal fun activeToolbar(enableHome: Boolean) {
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(enableHome)
    }
}