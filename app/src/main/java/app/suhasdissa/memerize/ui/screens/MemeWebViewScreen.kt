package app.suhasdissa.memerize.ui.screens

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun MemeWebViewScreen(modifier: Modifier = Modifier) {
    AndroidView(modifier = Modifier.fillMaxSize(), factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            loadUrl("https://memerize.glitch.me/")
            settings.javaScriptEnabled = true
        }
    })
}
