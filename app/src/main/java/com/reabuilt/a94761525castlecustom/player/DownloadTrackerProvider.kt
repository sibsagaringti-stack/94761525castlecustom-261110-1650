package com.reabuilt.a94761525castlecustom.player

import android.content.Context
import com.google.android.exoplayer2.database.DatabaseProvider
import com.google.android.exoplayer2.download.DownloadTracker

object DownloadTrackerProvider {
    private var instance: DownloadTracker? = null

    fun getInstance(context: Context, databaseProvider: DatabaseProvider): DownloadTracker {
        if (instance == null) {
            instance = DownloadTracker(
                context,
                com.google.android.exoplayer2.upstream.DefaultHttpDataSource.Factory(),
                databaseProvider
            )
        }
        return instance!!
    }
}
