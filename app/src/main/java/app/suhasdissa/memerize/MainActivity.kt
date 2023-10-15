/*******************************************************************************
Created By Suhas Dissanayake on 11/23/22, 4:16 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.suhasdissa.memerize.ui.MemerizeApp
import app.suhasdissa.memerize.ui.theme.MemerizeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, decorFitsSystemWindows: false)
        
        setContent {
            MemerizeTheme {
                MemerizeApp()
            }
        }
    }
}
