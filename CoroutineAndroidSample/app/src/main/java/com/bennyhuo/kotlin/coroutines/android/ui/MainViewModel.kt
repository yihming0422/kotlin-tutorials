package com.bennyhuo.kotlin.coroutines.android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bennyhuo.kotlin.coroutines.android.download.DownloadManager
import com.bennyhuo.kotlin.coroutines.android.download.DownloadManager.DownloadStatus
import com.bennyhuo.kotlin.coroutines.android.download.DownloadManager.DownloadStatus.None
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn

class MainViewModel : ViewModel() {

    val downloadStatusLiveData = MutableLiveData<DownloadStatus>(None)

    suspend fun download(url: String, fileName: String){
        DownloadManager.download(url, fileName)
                .flowOn(Dispatchers.IO)
                .collect {
                    downloadStatusLiveData.value = it
                }
    }

}