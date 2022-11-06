package app.suhasdissa.memerize.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier,
    onClickMemeView:() -> Unit = {},
    onClickMemeWebView:() -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column() {
            Button(onClick = {onClickMemeView() }) {
                Text(text = "MemeView")
            }
            Button(onClick = {onClickMemeWebView() }) {
                Text(text = "MemeWebView")
            }
        }
    }
}