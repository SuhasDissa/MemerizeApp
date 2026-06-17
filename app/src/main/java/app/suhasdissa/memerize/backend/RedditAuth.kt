package app.suhasdissa.memerize.backend

import android.content.Context
import androidx.core.content.edit
import app.suhasdissa.memerize.utils.preferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val REDDIT_COOKIE_KEY = "reddit_session_cookie"

object RedditAuth {
    private var _cookie: String? = null
    private val _isAuthenticated = MutableStateFlow<Boolean?>(null)
    val isAuthenticated: StateFlow<Boolean?> = _isAuthenticated.asStateFlow()

    fun initialize(context: Context) {
        _cookie = context.preferences.getString(REDDIT_COOKIE_KEY, null)
        _isAuthenticated.value = _cookie != null
    }

    fun getCookie(): String? = _cookie

    fun saveCookie(context: Context, cookie: String) {
        _cookie = cookie
        _isAuthenticated.value = true
        context.preferences.edit { putString(REDDIT_COOKIE_KEY, cookie) }
    }

    fun clearAuth(context: Context) {
        _cookie = null
        _isAuthenticated.value = false
        context.preferences.edit { remove(REDDIT_COOKIE_KEY) }
    }
}
