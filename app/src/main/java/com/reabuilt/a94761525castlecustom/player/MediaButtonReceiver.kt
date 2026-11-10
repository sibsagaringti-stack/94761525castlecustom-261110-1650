package com.reabuilt.a94761525castlecustom.player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.media3.session.MediaButtonReceiver

class MediaButtonReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        MediaButtonReceiver.handleMediaButtonIntent(context, intent)
    }
}
