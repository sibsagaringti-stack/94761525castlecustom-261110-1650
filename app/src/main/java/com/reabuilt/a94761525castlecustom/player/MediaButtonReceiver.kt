package com.reabuilt.a94761525castlecustom.player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import androidx.media3.common.MediaController

class MediaButtonReceiver : BroadcastReceiver() {

    companion object {
        /**
         * The player currently attached to the active PlayerScreen. Wired by the
         * screen so hardware media buttons (play / pause / next / previous) work.
         */
        @Volatile
        var activePlayer: MediaController? = null
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_MEDIA_BUTTON) return

        @Suppress("DEPRECATION")
        val event: KeyEvent? = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT)
        if (event == null || event.action != KeyEvent.KEY_EVENT_DOWN) return

        val player = activePlayer ?: return
        when (event.keyCode) {
            KeyEvent.KEYCODE_MEDIA_PLAY, KeyEvent.KEYCODE_HEADSETHOOK -> player.play()
            KeyEvent.KEYCODE_MEDIA_PAUSE -> player.pause()
            KeyEvent.KEYCODE_MEDIA_NEXT -> player.seekToNext()
            KeyEvent.KEYCODE_MEDIA_PREVIOUS -> player.seekToPrevious()
            KeyEvent.KEYCODE_MEDIA_FAST_FORWARD -> player.seekForward()
            KeyEvent.KEYCODE_MEDIA_REWIND -> player.seekBack()
        }
    }
}
