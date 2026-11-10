package com.reabuilt.a94761525castlecustom.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import androidx.media3.session.MediaStyleNotificationData
import androidx.media3.session.mediaStyleNotificationDataToBuilder
import com.reabuilt.a94761525castlecustom.R

class CastleMediaPlaybackService : MediaSessionService() {

    private var mediaSession: MediaSession? = null
    private var exoPlayer: ExoPlayer? = null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val player = ExoPlayer.Builder(this).build()
        exoPlayer = player
        mediaSession = MediaSession.Builder(this, player, "Castle Custom Playback", null).build()
        startForeground(NOTIFICATION_ID, buildNotification("Castle Custom", "Media playback ready"))
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession {
        return mediaSession ?: MediaSession
            .Builder(this, ExoPlayer.Builder(this).build(), "Castle Custom Playback", null)
            .build()
            .also { mediaSession = it }
    }

    override fun onDestroy() {
        mediaSession?.release()
        exoPlayer?.release()
        mediaSession = null
        exoPlayer = null
        super.onDestroy()
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(CHANNEL_ID, "Playback", NotificationManager.IMPORTANCE_LOW)
        channel.description = "Shows media playback controls for Castle Custom"
        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .createNotificationChannel(channel)
    }

    private fun buildNotification(title: String, text: String): Notification {
        val builder = Notification.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher)
            .setOngoing(true)
            .setContentTitle(title)
            .setContentText(text)
        mediaSession?.sessionToken?.let { token ->
            val data = MediaStyleNotificationData.Builder()
                .setSessionToken(token)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_launcher)
                .build()
            builder.apply { mediaStyleNotificationDataToBuilder(data, this) }
        }
        return builder.build()
    }

    companion object {
        const val CHANNEL_ID = "castle_playback"
        const val NOTIFICATION_ID = 42
    }
}
