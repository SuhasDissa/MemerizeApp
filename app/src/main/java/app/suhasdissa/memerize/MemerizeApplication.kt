/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 6:27 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import android.app.Application
import app.suhasdissa.memerize.backend.databases.MemeDatabase
import app.suhasdissa.memerize.utils.UpdateUtil

class MemerizeApplication : Application() {
    private val database by lazy { MemeDatabase.getDatabase(this) }
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(database)
        UpdateUtil.getCurrentVersion(this.applicationContext)
    }
}
