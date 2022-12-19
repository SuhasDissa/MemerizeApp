/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.utils

import android.app.DownloadManager
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.net.Uri
import android.os.Environment
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun downloadUtil(context: Context, url: String) {
    if (url.contains("v.redd.it")) {
        val vidUrl = url.split("/").slice(0..3).joinToString("/")
        val openUrl = URLEncoder.encode(vidUrl, StandardCharsets.UTF_8.toString())
        openBrowser(context, "https://redditsave.com/info?url=$openUrl")
    } else {
        val filename = url.split("/").last()
        val request = DownloadManager.Request(Uri.parse(url))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("Memerize").setDescription("Downloading $filename").setRequiresCharging(false)
            .setAllowedOverMetered(true).setAllowedOverRoaming(true)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename)

        val downloadManager = context.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }
}