/*******************************************************************************
 Created By Suhas Dissanayake on 11/23/22, 4:16 PM
 Copyright (c) 2022
 https://github.com/SuhasDissa/
 All Rights Reserved
 ******************************************************************************/

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