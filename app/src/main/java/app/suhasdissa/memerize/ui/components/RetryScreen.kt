/*******************************************************************************
Created By Suhas Dissanayake on 5/10/23, 8:57 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.ui.components

import android.view.SoundEffectConstants
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView

@Composable
fun RetryScreen(
    message: String,
    btnText: String,
    modifier: Modifier = Modifier,
    onRetry: () -> Unit
) {
    val view = LocalView.current
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                message,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyLarge
            )
            Button(onClick = {
                view.playSoundEffect(SoundEffectConstants.CLICK)
                onRetry()
            }) {
                Text(btnText)
            }
        }
    }
}
