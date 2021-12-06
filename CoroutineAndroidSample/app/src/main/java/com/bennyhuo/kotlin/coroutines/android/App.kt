package com.bennyhuo.kotlin.coroutines.android

import android.app.Application
import com.bennyhuo.kotlin.coroutines.android.exception.GlobalThreadUncaughtExceptionHandler
import com.bennyhuo.kotlin.coroutines.android.settings.Settings
import timber.log.Timber

lateinit var appContext: Application
    private set

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this

        Timber.plant(Timber.DebugTree())

        GlobalThreadUncaughtExceptionHandler.setUp()

        if(Settings.firstTimeLaunch){
            Timber.d("First time launch.")
            Settings.firstTimeLaunch = false

            // do something.
        }
    }

}