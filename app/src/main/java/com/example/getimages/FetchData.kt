package com.example.getimages

import android.os.AsyncTask
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    SUCCESS, IDLE, NOT_STARTED, FAILED_OR_EMPTY, PERMISSIONS_ERROR, ERROR
}

interface OnDownloadComplete {
    fun onDownloadComplete(data: String, status: DownloadStatus)
}

// Void means the progress parameter
class FetchData(private val listener: OnDownloadComplete) : AsyncTask<String, Void, String>() {
    private var downloadStatus = DownloadStatus.IDLE

    override fun onPostExecute(result: String) {
        listener.onDownloadComplete(result, downloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null) {
            downloadStatus = DownloadStatus.NOT_STARTED
            return "No URL specified"
        }

        try {
            downloadStatus = DownloadStatus.SUCCESS
            return URL(params[0]).readText()
        } catch (e: Exception) {
            return when (e) {
                is MalformedURLException -> {
                    downloadStatus = DownloadStatus.NOT_STARTED
                    "doInBackground: Invalid URL ${e.message}"
                }
                is IOException -> {
                    downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                    "doInBackground: IO Exception reading data ${e.message}"
                }
                is SecurityException -> {
                    downloadStatus = DownloadStatus.PERMISSIONS_ERROR
                    "doInBackground: Security Exception ${e.message}"
                }
                else -> {
                    downloadStatus = DownloadStatus.ERROR
                    "doInBackground: Unknown error ${e.message}"
                }
            }
        }
    }
}