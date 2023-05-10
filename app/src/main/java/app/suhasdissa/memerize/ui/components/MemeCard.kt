/*******************************************************************************
Created By Suhas Dissanayake on 12/20/22, 8:56 AM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun MemeCard(
    onClickMeme: (url: String) -> Unit, photo: String, title: String, modifier: Modifier = Modifier
) {
    val encodedImg = URLEncoder.encode(photo, StandardCharsets.UTF_8.toString())

    CardImage(modifier, { onClickMeme(encodedImg) }, photo, title)
}