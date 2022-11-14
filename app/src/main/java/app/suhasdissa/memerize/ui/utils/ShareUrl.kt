package app.suhasdissa.memerize.ui.utils

import android.content.Context
import android.content.Intent

fun ShareUrl(context: Context, url:String){
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_TEXT, url)
        type = "text/plain"
    }
    val shareIntent = Intent.createChooser(sendIntent, "Send Photo to..")
    context.startActivity(shareIntent)
}