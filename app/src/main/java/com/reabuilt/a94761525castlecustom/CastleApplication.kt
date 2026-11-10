package com.reabuilt.a94761525castlecustom

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.database.ExoDatabaseProvider
import com.google.android.exoplayer2.download.DownloadService
import com.google.android.exoplayer2.download.DownloadTracker
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.upstream.HttpDataSource
import com.reabuilt.a94761525castlecustom.player.CastleMediaPlaybackService
import com.reabuilt.a94761525castlecustom.player.DownloadTrackerProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class CastleApplication : Application() {

    private val mainScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var downloadTracker: DownloadTracker? = null

    override fun onCreate() {
        super.onCreate()

        // Initialize ExoPlayer database provider
        val databaseProvider: DatabaseProvider = ExoDatabaseProvider(this)

        // Initialize download tracker
        downloadTracker = DownloadTrackerProvider.getInstance(this, databaseProvider)

        // Initialize download service
        DownloadService.setDownloadServiceClass(CastleMediaPlaybackService::class.java)

        // Configure default HTTP data source
        DefaultHttpDataSource.Factory().setDefaultRequestProperties(
            mapOf("User-Agent" to "CastleCustom/2.1.3 (Linux; Android)")
        )

        ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver())
    }

    fun getMainScope(): CoroutineScope = mainScope

    fun getDownloadTracker(): DownloadTracker = downloadTracker!!
}

class AppLifecycleObserver : androidx.lifecycle.DefaultLifecycleObserver {
    override fun onStart(owner: androidx.lifecycle.LifecycleOwner) {
        // App entered foreground
    }

    override fun onStop(owner: androidx.lifecycle.LifecycleOwner) {
        // App entered background
    }
}
