package app.suhasdissa.memerize.ui.utils

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import android.widget.Toast

fun DownloadUtil(context: Context, url: String) {
    val filename = url.split("/").last()
    val request = DownloadManager.Request(Uri.parse(url))
        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        .setTitle("Memerize")
        .setDescription("Downloading $filename")
        .setRequiresCharging(false)
        .setAllowedOverMetered(true)
        .setAllowedOverRoaming(true)
        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

    try {
        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    } catch (err: Exception) {
        Toast.makeText(context, err.toString(), Toast.LENGTH_LONG).show();
    }
}