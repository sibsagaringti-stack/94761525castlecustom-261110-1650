package com.reabuilt.a94761525castlecustom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FeaturedCollection(
    val id: String,
    val title: String,
    val description: String,
    val content: List<Content>,
    val bannerUrl: String
)

@Serializable
data class WatchHistoryItem(
    val contentId: String,
    val content: Content,
    val watchedAt: Long,
    val progress: Int,
    val episodeId: String?
)

@Serializable
data class DownloadItem(
    val id: String,
    val contentId: String,
    val content: Content,
    val downloadUrl: String,
    val quality: String,
    val progress: Int,
    val status: DownloadStatus,
    val fileSize: Double,
    val downloadedAt: Long,
    val episodeId: String?
)

enum class DownloadStatus {
    QUEUED,
    DOWNLOADING,
    PAUSED,
    COMPLETED,
    FAILED,
    REMOVED
}
