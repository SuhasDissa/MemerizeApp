package app.suhasdissa.memerize.utils

import android.content.Context

fun getSettingString(context: Context, key: String, defValue: String): String {
    val sharedPref = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)
    return sharedPref.getString(key, defValue) ?: return defValue
}

fun applySettingString(context: Context, key: String, newValue: String) {
    val sharedPref = context.getSharedPreferences("SETTINGS", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString(key, newValue)
        apply()
    }

}