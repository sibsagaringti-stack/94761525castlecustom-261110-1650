package com.reabuilt.a94761525castlecustom.data

import com.reabuilt.a94761525castlecustom.data.model.Content
import com.reabuilt.a94761525castlecustom.data.model.ContentType
import com.reabuilt.a94761525castlecustom.data.model.Episode
import com.reabuilt.a94761525castlecustom.data.model.Season
import com.reabuilt.a94761525castlecustom.data.model.VideoQuality

fun VideoQuality.hlsKey(): String = when (this) {
    VideoQuality.SD -> "480p"
    VideoQuality.HD -> "720p"
    VideoQuality.FULL_HD -> "1080p"
    VideoQuality.ULTRA_HD -> "2160p"
}

fun pickStreamUrl(streamUrls: Map<String, String>, quality: VideoQuality): String? {
    streamUrls[quality.hlsKey()]?.let { return it }
    return streamUrls.values.firstOrNull()
}

fun generateSeasonsFor(content: Content): List<Season> {
    val isSeries = content.contentType == ContentType.TV_SHOW ||
        content.contentType == ContentType.WEB_SERIES ||
        content.contentType == ContentType.ANIME
    if (!isSeries) return emptyList()

    val seasonCount = if (content.seasonCount > 0) content.seasonCount else 1
    val totalEpisodes = if (content.episodeCount > 0) content.episodeCount else seasonCount * 6
    val episodesPerSeason = (totalEpisodes / seasonCount).coerceAtLeast(1)

    return (1..seasonCount).map { seasonNumber ->
        val episodesInSeason = if (seasonNumber == seasonCount) {
            (totalEpisodes - (seasonNumber - 1) * episodesPerSeason).coerceAtLeast(1)
        } else {
            episodesPerSeason
        }
        Season(
            seasonNumber = seasonNumber,
            name = "Season $seasonNumber",
            episodeCount = episodesInSeason,
            posterUrl = content.backdropUrl,
            episodes = (1..episodesInSeason).map { episodeNumber ->
                Episode(
                    episodeNumber = episodeNumber,
                    seasonNumber = seasonNumber,
                    title = "Episode $episodeNumber",
                    description = "Season $seasonNumber, Episode $episodeNumber of ${content.title}.",
                    durationMinutes = content.durationMinutes ?: 42,
                    streamUrls = content.streamUrls,
                    subtitleUrls = emptyMap(),
                    thumbnailUrl = content.backdropUrl,
                    airDate = null,
                    isWatched = false
                )
            }
        )
    }
}

fun episodeIdFor(contentId: String, seasonNumber: Int, episodeNumber: Int): String =
    "${contentId}_s${seasonNumber}e${episodeNumber}"

fun findEpisode(content: Content, episodeId: String?): Episode? {
    if (episodeId.isNullOrBlank()) return null
    return generateSeasonsFor(content).flatMap { it.episodes }.firstOrNull {
        episodeIdFor(content.id, it.seasonNumber, it.episodeNumber) == episodeId
    }
}
