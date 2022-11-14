package app.suhasdissa.memerize.ui.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun OpenBrowser(context: Context, url:String){
    val viewIntent: Intent = Intent().apply {
        action = Intent.ACTION_VIEW
        setData(Uri.parse(url))
    }
    context.startActivity(viewIntent)
}