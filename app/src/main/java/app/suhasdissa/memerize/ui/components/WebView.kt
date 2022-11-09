package app.suhasdissa.memerize.ui.components

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun WebViewer(url:String,modifier: Modifier = Modifier) {
    val decodedUrl = URLDecoder.decode(url, StandardCharsets.UTF_8.toString())
    AndroidView(modifier = Modifier.fillMaxSize(), factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            loadUrl(decodedUrl)
            settings.javaScriptEnabled = true
        }
    })
}