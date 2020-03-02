package com.example.getimages

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoProvider


class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var thumbnail: ImageView = view.findViewById(R.id.thumbnail)
    var title: TextView = view.findViewById(R.id.title)
}

class ImageRecyclerViewAdapter(private var imageList: List<ImagePayload>) : RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        // creates layouts dynamically when required
        val view = LayoutInflater.from(parent.context).inflate(R.layout.browse, parent, false)
        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int {
        // returns the number of images in the list
        return if (imageList.isNotEmpty()) imageList.size else 1 // means if no image, show placeholder
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {

        if (imageList.isEmpty()) {
            holder.thumbnail.setImageResource(R.drawable.ic_launcher_foreground)
            holder.title.setText(R.string.empty_search)
        } else {
            // called when need new data in a existing view
            val imageItem = imageList[position]

            Picasso.get().load(imageItem.image).error(R.drawable.ic_launcher_foreground)
                .placeholder(R.drawable.ic_launcher_foreground).into(holder.thumbnail)

            holder.title.text = imageItem.title
        }
    }

    fun loadNewData(newImage: List<ImagePayload>) {
        imageList = newImage
        notifyDataSetChanged()
    }

    fun getImages(position: Int): ImagePayload? {
        return if (imageList.isNotEmpty()) imageList[position] else null
    }
}