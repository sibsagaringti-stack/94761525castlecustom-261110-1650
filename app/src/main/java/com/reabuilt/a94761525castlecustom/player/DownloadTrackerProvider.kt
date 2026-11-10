package com.reabuilt.a94761525castlecustom.player

import android.content.Context
import androidx.media3.database.DatabaseProvider
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.offline.DownloadManager
import androidx.media3.exoplayer.offline.DownloadTracker

object DownloadTrackerProvider {
    
    @Volatile
    private var instance: DownloadTracker? = null
    
    fun getInstance(context: Context, databaseProvider: DatabaseProvider): DownloadTracker {
        return instance ?: synchronized(this) {
            val tracker = DownloadTracker(
                context,
                DefaultHttpDataSource.Factory(),
                databaseProvider
            )
            instance = tracker
            tracker
        }
    }
}
