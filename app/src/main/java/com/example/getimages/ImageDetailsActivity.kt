package com.example.getimages

import android.os.Bundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.browse.*
import kotlinx.android.synthetic.main.content_image_details.*

class ImageDetailsActivity : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_details)
        activeToolbar(true)

        //val image = intent.getSerializableExtra(IMAGE_TRANSFER) as ImagePayload
        val image = intent.extras!!.getParcelable<ImagePayload>(IMAGE_TRANSFER) as ImagePayload

        image_title.text = image.title
        image_tags.text = image.tags
        image_author.text = image.author

        Picasso.get().load(image.link).error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground).into(photo_image)
    }

}
