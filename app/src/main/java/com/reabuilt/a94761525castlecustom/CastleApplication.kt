package com.reabuilt.a94761525castlecustom

import android.app.Application
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.reabuilt.a94761525castlecustom.data.repository.ContentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CastleApplication : Application() {

    private val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    val contentRepository: ContentRepository by lazy { ContentRepository(this) }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
    }

    fun getMainScope(): CoroutineScope = mainScope
}

class AppLifecycleObserver : DefaultLifecycleObserver {

    override fun onStart(owner: LifecycleOwner) {
        // App returned to the foreground.
    }

    override fun onStop(owner: LifecycleOwner) {
        // App moved to the background.
    }
}
