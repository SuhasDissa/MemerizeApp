/*******************************************************************************
Created By Suhas Dissanayake on 11/25/22, 6:27 PM
Copyright (c) 2022
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize

import android.app.Application
import app.suhasdissa.memerize.backend.database.MemeDatabase
import app.suhasdissa.memerize.utils.UpdateUtil
import app.suhasdissa.memerize.utils.defaultImageCacheSize
import app.suhasdissa.memerize.utils.imageCacheKey
import app.suhasdissa.memerize.utils.preferences
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache

class MemerizeApplication : Application(), ImageLoaderFactory {
    private val database by lazy { MemeDatabase.getDatabase(this) }
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(database)
        UpdateUtil.getCurrentVersion(this.applicationContext)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .respectCacheHeaders(false)
            .diskCache(
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizeBytes(
                        preferences.getInt(imageCacheKey, defaultImageCacheSize) * 1024 * 1024L
                    )
                    .build()
            ).build()
    }
}
