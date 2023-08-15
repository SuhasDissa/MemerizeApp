/*******************************************************************************
Created By Suhas Dissanayake on 12/20/22, 8:56 AM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.components

import androidx.compose.runtime.Composable

@Composable
fun MemeCard(
    onClickMeme: () -> Unit,
    photo: String,
    title: String
) {
    ImageCard({ onClickMeme.invoke() }, photo, title)
}
