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
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.request.crossfade
import okio.Path.Companion.toOkioPath

class MemerizeApplication : Application(), SingletonImageLoader.Factory {
    private val database by lazy { MemeDatabase.getDatabase(this) }
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer(database)
        UpdateUtil.getCurrentVersion(this.applicationContext)
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .diskCache(
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache").toOkioPath())
                    .maxSizeBytes(
                        preferences.getInt(imageCacheKey, defaultImageCacheSize) * 1024 * 1024L
                    )
                    .build()
            ).build()
    }
}
