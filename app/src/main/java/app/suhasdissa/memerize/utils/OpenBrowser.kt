/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.utils

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openBrowser(context: Context, url: String) {
    val viewIntent: Intent = Intent().apply {
        action = Intent.ACTION_VIEW
        data = Uri.parse(url)
    }
    context.startActivity(viewIntent)
}
