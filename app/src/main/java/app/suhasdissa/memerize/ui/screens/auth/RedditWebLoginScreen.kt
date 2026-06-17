package app.suhasdissa.memerize.ui.screens.auth

import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import app.suhasdissa.memerize.backend.RedditAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RedditWebLoginScreen(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Log in to Reddit") })
        }
    ) { innerPadding ->
        AndroidView(
            factory = { ctx ->
                WebView(ctx).apply {
                    settings.javaScriptEnabled = true
                    settings.domStorageEnabled = true
                    webViewClient = object : WebViewClient() {
                        override fun onPageFinished(view: WebView, url: String?) {
                            if (url == null) return
                            val isLoginPage = url.contains("reddit.com/login") ||
                                url.contains("reddit.com/register") ||
                                url.contains("reddit.com/account/register")
                            if (!isLoginPage) {
                                val cookies = CookieManager.getInstance()
                                    .getCookie("https://www.reddit.com")
                                if (cookies != null && cookies.contains("reddit_session")) {
                                    RedditAuth.saveCookie(context.applicationContext, cookies)
                                    onLoginSuccess()
                                }
                            }
                        }
                    }
                    loadUrl("https://www.reddit.com/login")
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        )
    }
}
