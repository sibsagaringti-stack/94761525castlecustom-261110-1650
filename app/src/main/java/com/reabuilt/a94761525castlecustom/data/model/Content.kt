package com.reabuilt.a94761525castlecustom.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Content(
    val id: String,
    val title: String,
    val description: String,
    val posterUrl: String,
    val backdropUrl: String?,
    val contentType: ContentType,
    val genres: List<String>,
    val languages: List<String>,
    val releaseYear: Int,
    val durationMinutes: Int?,
    val rating: Float,
    val voteCount: Int,
    val isTrending: Boolean = false,
    val isFeatured: Boolean = false,
    val isNewRelease: Boolean = false,
    val quality: VideoQuality = VideoQuality.HD,
    val ageRating: String = "U/A",
    val cast: List<String> = emptyList(),
    val crew: List<String> = emptyList(),
    val seasonCount: Int = 0,
    val episodeCount: Int = 0,
    val trailerUrl: String? = null,
    val streamUrls: Map<String, String> = emptyMap(),
    val subtitleUrls: Map<String, String> = emptyMap(),
    val isLive: Boolean = false,
    val channelNumber: Int? = null,
    val category: String? = null
)

enum class ContentType(val displayName: String) {
    MOVIE("Movie"),
    TV_SHOW("TV Show"),
    WEB_SERIES("Web Series"),
    ANIME("Anime"),
    LIVE_TV("Live TV"),
    SHORT("Short"),
    DOCUMENTARY("Documentary")
}

enum class VideoQuality(val displayName: String, val height: Int) {
    SD("SD", 480),
    HD("HD", 720),
    FULL_HD("Full HD", 1080),
    ULTRA_HD("4K", 2160)
}

@Serializable
data class ContentDetail(
    val content: Content,
    val seasons: List<Season> = emptyList(),
    val similarContent: List<Content> = emptyList(),
    val watchProgress: WatchProgress? = null
)

@Serializable
data class Season(
    val seasonNumber: Int,
    val name: String,
    val episodeCount: Int,
    val episodes: List<Episode> = emptyList(),
    val posterUrl: String? = null
)

@Serializable
data class Episode(
    val episodeNumber: Int,
    val seasonNumber: Int,
    val title: String,
    val description: String,
    val durationMinutes: Int,
    val streamUrls: Map<String, String>,
    val subtitleUrls: Map<String, String>,
    val thumbnailUrl: String?,
    val airDate: String?,
    val isWatched: Boolean = false,
    val watchProgress: WatchProgress? = null
)

@Serializable
data class WatchProgress(
    val contentId: String,
    val episodeId: String?,
    val positionMs: Long,
    val durationMs: Long,
    val completed: Boolean = false,
    val lastUpdated: Long = System.currentTimeMillis()
)

@Serializable
data class LiveChannel(
    val id: String,
    val name: String,
    val logoUrl: String,
    val streamUrl: String,
    val category: LiveTvCategory,
    val channelNumber: Int,
    val isLive: Boolean = true,
    val currentProgram: String? = null,
    val nextProgram: String? = null,
    val epg: List<EpgEntry> = emptyList()
)

enum class LiveTvCategory(val displayName: String, val icon: String) {
    NEWS("News", "newspaper"),
    SPORTS("Sports", "sports"),
    MOVIES("Movies", "movie"),
    MUSIC("Music", "music"),
    KIDS("Kids", "child"),
    DEVOTIONAL("Devotional", "church"),
    ENTERTAINMENT("Entertainment", "tv"),
    LIFESTYLE("Lifestyle", "spa"),
    KNOWLEDGE("Knowledge", "school"),
    REGIONAL("Regional", "map")
}

@Serializable
data class EpgEntry(
    val startTime: Long,
    val endTime: Long,
    val title: String,
    val description: String
)

@Serializable
data class SearchSuggestion(
    val id: String,
    val query: String,
    val type: SuggestionType,
    val contentId: String? = null,
    val contentType: ContentType? = null
)

enum class SuggestionType {
    QUERY,
    CONTENT,
    PERSON,
    GENRE
}

@Serializable
data class UserPreferences(
    val preferredLanguages: List<String> = listOf("Hindi", "English"),
    val preferredGenres: List<String> = emptyList(),
    val videoQuality: VideoQuality = VideoQuality.HD,
    val autoPlayNext: Boolean = true,
    val downloadsOverWifiOnly: Boolean = true,
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val enableNotifications: Boolean = true,
    val parentalControlEnabled: Boolean = false,
    val parentalControlPin: String? = null
)

enum class ThemeMode {
    SYSTEM, LIGHT, DARK
}
