package com.reabuilt.a94761525castlecustom.player

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.view.KeyEvent
import androidx.media3.common.C
import androidx.media3.session.MediaController

class MediaButtonReceiver : BroadcastReceiver() {

    companion object {
        /**
         * The player currently attached to the active PlayerScreen. Wired by the
         * screen so hardware media buttons (play / pause / next / previous) work.
         */
        @Volatile
        var activePlayer: MediaController? = null

        private const val SEEK_FORWARD_MS = 15_000L
        private const val SEEK_BACK_MS = 10_000L
    }

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_MEDIA_BUTTON) return

        @Suppress("DEPRECATION")
        val event: KeyEvent? = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT)
        if (event == null || event.action != KeyEvent.ACTION_DOWN) return

        val player = activePlayer ?: return
        when (event.keyCode) {
            KeyEvent.KEYCODE_MEDIA_PLAY, KeyEvent.KEYCODE_HEADSETHOOK -> player.play()
            KeyEvent.KEYCODE_MEDIA_PAUSE -> player.pause()
            KeyEvent.KEYCODE_MEDIA_NEXT -> player.seekToNextMediaItem()
            KeyEvent.KEYCODE_MEDIA_PREVIOUS -> player.seekToPreviousMediaItem()
            KeyEvent.KEYCODE_MEDIA_FAST_FORWARD -> {
                val position = player.currentPosition
                if (position != C.TIME_UNSET) {
                    player.setPlaybackPosition(position + SEEK_FORWARD_MS)
                }
            }
            KeyEvent.KEYCODE_MEDIA_REWIND -> {
                val position = player.currentPosition
                if (position != C.TIME_UNSET) {
                    player.setPlaybackPosition(maxOf(0L, position - SEEK_BACK_MS))
                }
            }
        }
    }
}
