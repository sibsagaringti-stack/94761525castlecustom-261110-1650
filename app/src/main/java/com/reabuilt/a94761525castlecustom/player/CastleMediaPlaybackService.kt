package com.reabuilt.a94761525castlecustom.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.reabuilt.a94761525castlecustom.R

/**
 * Foreground media playback service for Castle Custom.
 *
 * It owns an [ExoPlayer] and a [MediaSession] so background playback can continue
 * while a persistent notification is shown. No ads / subscription logic is involved.
 */
class CastleMediaPlaybackService : MediaSessionService() {

    private var mediaSession: MediaSession? = null
    private var exoPlayer: ExoPlayer? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, buildNotification("Castle Custom", "Media playback ready"))
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        mediaSession?.let { return it }

        val player = ExoPlayer.Builder(this).build()
        exoPlayer = player

        val session = MediaSession.Builder(this, player).build()
        mediaSession = session
        return session
    }

    override fun onDestroy() {
        mediaSession?.release()
        exoPlayer?.release()
        mediaSession = null
        exoPlayer = null
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Playback",
            NotificationManager.IMPORTANCE_LOW
        )
        channel.description = "Shows media playback status for Castle Custom"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun buildNotification(title: String, text: String): Notification {
        return Notification.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(title)
            .setContentText(text)
            .setOngoing(true)
            .setShowWhen(false)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()
    }

    companion object {
        const val CHANNEL_ID = "castle_playback"
        const val NOTIFICATION_ID = 42
    }
}
