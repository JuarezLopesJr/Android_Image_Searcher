package com.example.getimages

import android.os.AsyncTask
import android.util.Log
import org.json.JSONException
import org.json.JSONObject

interface OnDataAvailable {
    fun onDataAvailable(data: List<ImagePayload>)
    fun onError(error: Exception)
}

class GetJsonData(private val listener: OnDataAvailable) : AsyncTask<String, Void, ArrayList<ImagePayload>>() {
    override fun onPostExecute(result: ArrayList<ImagePayload>) {
        super.onPostExecute(result)
        listener.onDataAvailable(result)
    }

    override fun doInBackground(vararg params: String?): ArrayList<ImagePayload> {

        val imageList = ArrayList<ImagePayload>()

        try {
            val jsonData = JSONObject(params[0])
            // passing the actual array name to the function call, "items" in this case
            val itemsArray = jsonData.getJSONArray("items")

            for (item in 0 until itemsArray.length()) {
                val jsonImage = itemsArray.getJSONObject(item)
                val title = jsonImage.getString("title")
                val author = jsonImage.getString("author")
                val authorId = jsonImage.getString("author_id")
                val tags = jsonImage.getString("tags")
                val jsonMedia = jsonImage.getJSONObject("media")
                val photoUrl = jsonMedia.getString("m")
                val link = photoUrl.replaceFirst("_m.jpg", "_b.jpg")

                val imageObject = ImagePayload(title, author, authorId, link, tags, photoUrl)

                imageList.add(imageObject)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e("Getting JSON", "inside doInBackground: ${e.message}")
            // canceling listener - cancel(true) - actual function call
            listener.onError(e)
        }

        return imageList
    }
}