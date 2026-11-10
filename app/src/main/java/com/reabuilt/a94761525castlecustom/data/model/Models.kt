package com.reabuilt.a94761525castlecustom.data.model

import kotlinx.serialization.Serializable

/**
 * Single source of truth for download-related models.
 *
 * Merged superset of the previously duplicated declarations in Models.kt and
 * ExtraModels.kt so every field used anywhere in the app is available:
 *  - Models.kt side:  sizeBytes, DownloadStatus.displayName
 *  - ExtraModels.kt side: contentId, downloadUrl, quality, fileSize, REMOVED
 */
@Serializable
data class DownloadItem(
    val id: String,
    val contentId: String = "",
    val content: Content,
    val episodeId: String? = null,
    val downloadUrl: String = "",
    val quality: String = "720p",
    val status: DownloadStatus = DownloadStatus.DOWNLOADING,
    val progress: Int = 0,
    val sizeBytes: Long = 0L,
    val fileSize: Double = 0.0,
    val downloadedAt: Long = System.currentTimeMillis()
)

enum class DownloadStatus(val displayName: String) {
    QUEUED("Queued"),
    DOWNLOADING("Downloading"),
    PAUSED("Paused"),
    COMPLETED("Completed"),
    FAILED("Failed"),
    REMOVED("Removed")
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
