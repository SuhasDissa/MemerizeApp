package app.suhasdissa.memerize.utils

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment

fun downloadUtil(context: Context, url: String) {
    val filename = url.split("/").last()
    val request = DownloadManager.Request(Uri.parse(url))
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setTitle("Memerize")
        .setDescription("Downloading $filename")
        .setRequiresCharging(false)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)

    val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)

}