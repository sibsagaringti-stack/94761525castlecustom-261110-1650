package com.reabuilt.a94761525castlecustom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class DownloadItem(
    val id: String,
    val content: Content,
    val episodeId: String? = null,
    val status: DownloadStatus = DownloadStatus.DOWNLOADING,
    val progress: Int = 0,
    val sizeBytes: Long = 0L,
    val downloadedAt: Long = System.currentTimeMillis()
)

enum class DownloadStatus(val displayName: String) {
    QUEUED("Queued"),
    DOWNLOADING("Downloading"),
    PAUSED("Paused"),
    COMPLETED("Completed"),
    FAILED("Failed")
}

@Serializable
data class FeaturedCollection(
    val id: String,
    val title: String,
    val description: String,
    val content: List<Content>,
    val bannerUrl: String? = null
)

@Serializable
data class WatchHistoryItem(
    val contentId: String,
    val content: Content,
    val watchedAt: Long,
    val progress: Int,
    val episodeId: String? = null
)
