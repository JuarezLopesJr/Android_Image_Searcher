package com.example.getimages

import android.os.Parcel
import android.os.Parcelable


class ImagePayload(
    var title: String?,
    var author: String?,
    var authorId: String?,
    var link: String?,
    var tags: String?,
    var image: String?
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun toString(): String {
        return "Image(title = '$title', author = '$author', authorId = '$authorId', link = '$link', tags = '$tags', image = '$image')"
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<ImagePayload> {
        override fun createFromParcel(parcel: Parcel): ImagePayload {
            return ImagePayload(parcel)
        }

        override fun newArray(size: Int): Array<ImagePayload?> {
            return arrayOfNulls(size)
        }
    }
}