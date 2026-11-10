package com.reabuilt.a94761525castlecustom

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CastleApplication : Application() {

    private val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
    }

    fun getMainScope(): CoroutineScope = mainScope
}

class AppLifecycleObserver : androidx.lifecycle.DefaultLifecycleObserver {
    override fun onStart(owner: androidx.lifecycle.LifecycleOwner) {
        // App entered foreground
    }

    override fun onStop(owner: androidx.lifecycle.LifecycleOwner) {
        // App entered background
    }
}
