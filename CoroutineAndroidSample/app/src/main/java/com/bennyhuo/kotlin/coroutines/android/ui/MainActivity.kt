package com.bennyhuo.kotlin.coroutines.android.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import com.bennyhuo.kotlin.coroutines.android.R
import com.bennyhuo.kotlin.coroutines.android.download.DownloadManager.DownloadStatus.*
import com.bennyhuo.kotlin.coroutines.android.utils.alert
import com.bennyhuo.kotlin.coroutines.android.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val scope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showDialogButton.setOnClickListener {
            lifecycleScope.launch {
                val myChoice = alert("Warning!", "Do you want this?")
                toast("My choice is: $myChoice")
            }
        }

        mainViewModel.downloadStatusLiveData.observe(this) {
            downloadStatus ->
            when(downloadStatus){
                null, None -> {
                    downloadButton.isEnabled = true
                    downloadButton.text = "Download"
                    downloadButton.setOnClickListener {
                        lifecycleScope.launch {
                            mainViewModel.download(
                                    "https://kotlinlang.org/docs/kotlin-docs.pdf",
                                    "Kotlin-Docs.pdf"
                            )
                        }
                    }
                }
                is Progress -> {
                    downloadButton.isEnabled = false
                    downloadButton.text = "Downloading(${downloadStatus.value})"
                }
                is Error -> {
                    toast(downloadStatus.throwable)
                    downloadButton.isEnabled = true
                    downloadButton.text = "Download Error"
                    downloadButton.setOnClickListener {
                        lifecycleScope.launch {
                            mainViewModel.download(
                                    "https://kotlinlang.org/docs/kotlin-docs.pdf",
                                    "Kotlin-Docs.pdf"
                            )
                        }
                    }
                }
                is Done -> {
                    toast(downloadStatus.file)
                    downloadButton.isEnabled = true
                    downloadButton.text = "Open File"
                    downloadButton.setOnClickListener {
                        Intent(Intent.ACTION_VIEW).also {
                            it.setDataAndType(FileProvider.getUriForFile(this, "${packageName}.provider", downloadStatus.file), "application/pdf")
                            it.flags = Intent.FLAG_ACTIVITY_NO_HISTORY or Intent.FLAG_GRANT_READ_URI_PERMISSION
                        }.also(::startActivity)
                    }
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}
