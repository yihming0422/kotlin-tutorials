package com.bennyhuo.kotlin.coroutines.android.utils

import android.app.AlertDialog
import android.content.Context
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun Context.alert(title: String, message: String): Boolean = suspendCancellableCoroutine {
    continuation ->
    AlertDialog.Builder(this)
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
                continuation.resume(false)
            }.setPositiveButton("Yes"){ dialog, _ ->
                dialog.dismiss()
                continuation.resume(true)
            }.setTitle(title).setMessage(message)
            .setOnCancelListener {
                continuation.resume(false)
            }.create()
            .also {dialog ->
                continuation.invokeOnCancellation {
                    dialog.dismiss()
                }
            }.show()
}