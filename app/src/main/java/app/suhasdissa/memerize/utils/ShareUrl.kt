/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.utils

import android.content.Context
import android.content.Intent

fun shareUrl(context: Context, url: String) {
    var shareurl = url
    if (url.contains("v.redd.it")) {
        shareurl = url.split("/").slice(0..3).joinToString("/")
    }
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, shareurl)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Send Photo to..")
    context.startActivity(shareIntent)
}
