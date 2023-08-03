/*******************************************************************************
Created By Suhas Dissanayake on 8/3/23, 12:11 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.viewmodels

import android.content.Context
import android.graphics.Bitmap
import android.os.Environment
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import java.io.File
import java.io.FileOutputStream
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoViewModel : ViewModel() {

    var downloadState: DownloadState by mutableStateOf(DownloadState.NotStarted)

    fun savePhotoToDisk(url: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                downloadState = DownloadState.Loading
            }
            val imageLoader = ImageLoader.Builder(context).build()
            val request = ImageRequest.Builder(context)
                .data(url)
                .build()
            val result = imageLoader.execute(request)

            if (result is SuccessResult) {
                try {
                    val outputStream =
                        FileOutputStream(
                            File(
                                Environment.getExternalStoragePublicDirectory(
                                    Environment.DIRECTORY_DOWNLOADS
                                ),
                                "${UUID.randomUUID()}.jpg"
                            )
                        )
                    val bitmap = result.drawable.toBitmap()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                    outputStream.flush()
                    outputStream.close()
                    withContext(Dispatchers.Main) {
                        downloadState = DownloadState.Success
                    }
                } catch (_: Exception) {
                    withContext(Dispatchers.Main) {
                        downloadState = DownloadState.Error
                    }
                }
            } else if (result is ErrorResult) {
                withContext(Dispatchers.Main) {
                    downloadState = DownloadState.Error
                }
            }
        }
    }
}

sealed interface DownloadState {
    object NotStarted : DownloadState
    object Success : DownloadState
    object Error : DownloadState
    object Loading : DownloadState
}
