package com.reabuilt.a94761525castlecustom.data.repository

import com.reabuilt.a94761525castlecustom.CastleApplication
import com.reabuilt.a94761525castlecustom.data.model.Content
import com.reabuilt.a94761525castlecustom.data.model.ContentType
import com.reabuilt.a94761525castlecustom.data.model.DownloadItem
import com.reabuilt.a94761525castlecustom.data.model.DownloadStatus
import com.reabuilt.a94761525castlecustom.data.model.FeaturedCollection
import com.reabuilt.a94761525castlecustom.data.model.LiveChannel
import com.reabuilt.a94761525castlecustom.data.model.LiveTvCategory
import com.reabuilt.a94761525castlecustom.data.model.UserPreferences
import com.reabuilt.a94761525castlecustom.data.model.VideoQuality
import com.reabuilt.a94761525castlecustom.data.model.WatchHistoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ContentRepository(private val application: CastleApplication) {

    private val _trendingContent = MutableStateFlow<List<Content>>(emptyList())
    private val _continueWatching = MutableStateFlow<List<Content>>(emptyList())
    private val _recommendedContent = MutableStateFlow<List<Content>>(emptyList())
    private val _featuredCollections = MutableStateFlow<List<FeaturedCollection>>(emptyList())
    private val _liveChannels = MutableStateFlow<List<LiveChannel>>(emptyList())
    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())
    private val _watchlist = MutableStateFlow<Set<String>>(emptySet())
    private val _watchHistory = MutableStateFlow<List<WatchHistoryItem>>(emptyList())
    private val _downloads = MutableStateFlow<List<DownloadItem>>(emptyList())
    private val _userPreferences = MutableStateFlow(UserPreferences())

    val trendingContent: StateFlow<List<Content>> = _trendingContent.asStateFlow()
    val continueWatching: StateFlow<List<Content>> = _continueWatching.asStateFlow()
    val recommendedContent: StateFlow<List<Content>> = _recommendedContent.asStateFlow()
    val featuredCollections: StateFlow<List<FeaturedCollection>> = _featuredCollections.asStateFlow()
    val liveChannels: StateFlow<List<LiveChannel>> = _liveChannels.asStateFlow()
    val searchHistory: StateFlow<List<String>> = _searchHistory.asStateFlow()
    val watchlist: StateFlow<Set<String>> = _watchlist.asStateFlow()
    val watchHistory: StateFlow<List<WatchHistoryItem>> = _watchHistory.asStateFlow()
    val downloads: StateFlow<List<DownloadItem>> = _downloads.asStateFlow()
    val userPreferences: StateFlow<UserPreferences> = _userPreferences.asStateFlow()

    init {
        loadMockData()
        loadUserPreferences()
    }

    private fun loadMockData() {
        application.getMainScope().launch {
            _trendingContent.value = generateTrendingContent()
            _continueWatching.value = generateContinueWatching()
            _recommendedContent.value = generateRecommendedContent()
            _featuredCollections.value = generateFeaturedCollections()
            _liveChannels.value = generateLiveChannels()
            _watchlist.value = setOf("1", "3", "5", "8", "12")
            _watchHistory.value = generateWatchHistory()
            _downloads.value = generateDownloads()
        }
    }

    private fun loadUserPreferences() {
        _userPreferences.value = UserPreferences(
            preferredLanguages = listOf("Hindi", "English", "Tamil"),
            preferredGenres = listOf("Action", "Thriller", "Comedy", "Drama"),
            videoQuality = VideoQuality.HD,
            autoPlayNext = true,
            downloadsOverWifiOnly = true,
            themeMode = ThemeMode.SYSTEM,
            enableNotifications = true
        )
    }

    private fun generateTrendingContent(): List<Content> {
        return listOf(
            Content(
                id = "1",
                title = "Pathaan",
                description = "An Indian spy action thriller film.",
                posterUrl = "https://image.tmdb.org/t/p/w500/8Vt6kHEHvjWr6zHX6GseEWYwK5W.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/8Vt6kHEHvjWr6zHX6GseEWYwK5W.jpg",
                contentType = ContentType.MOVIE,
                genres = listOf("Action", "Thriller"),
                languages = listOf("Hindi", "Tamil", "Telugu"),
                releaseYear = 2023,
                durationMinutes = 146,
                rating = 4.5f,
                voteCount = 12500,
                isTrending = true,
                isFeatured = true,
                quality = VideoQuality.FULL_HD,
                ageRating = "U/A 16+",
                cast = listOf("Shah Rukh Khan", "Deepika Padukone"),
                crew = listOf("Siddharth Anand (Director)"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/pathaan_480p.m3u8",
                    "720p" to "https://example.com/pathaan_720p.m3u8",
                    "1080p" to "https://example.com/pathaan_1080p.m3u8"
                )
            ),
            Content(
                id = "2",
                title = "Jawan",
                description = "A high-octane action thriller.",
                posterUrl = "https://image.tmdb.org/t/p/w500/5u2wKMMQVijEv8nKmcRJ0mKj2hV.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/5u2wKMMQVijEv8nKmcRJ0mKj2hV.jpg",
                contentType = ContentType.MOVIE,
                genres = listOf("Action", "Thriller", "Drama"),
                languages = listOf("Hindi", "Tamil", "Telugu"),
                releaseYear = 2023,
                durationMinutes = 169,
                rating = 4.6f,
                voteCount = 15200,
                isTrending = true,
                isFeatured = true,
                quality = VideoQuality.FULL_HD,
                ageRating = "U/A 16+",
                cast = listOf("Shah Rukh Khan", "Nayanthara"),
                streamUrls = mapOf(
                    "480p" to "https://example.com/jawan_480p.m3u8",
                    "720p" to "https://example.com/jawan_720p.m3u8"
                )
            ),
            Content(
                id = "3",
                title = "The Family Man",
                description = "A middle-class man secretly works as an intelligence officer.",
                posterUrl = "https://image.tmdb.org/t/p/w500/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
                contentType = ContentType.WEB_SERIES,
                genres = listOf("Action", "Thriller", "Drama"),
                languages = listOf("Hindi", "Tamil"),
                releaseYear = 2019,
                durationMinutes = null,
                rating = 4.8f,
                voteCount = 25000,
                isTrending = true,
                seasonCount = 2,
                episodeCount = 19,
                quality = VideoQuality.FULL_HD,
                ageRating = "A",
                cast = listOf("Manoj Bajpayee", "Priyamani"),
                streamUrls = mapOf("720p" to "https://example.com/tfm_720p.m3u8")
            ),
            Content(
                id = "4",
                title = "Mirzapur",
                description = "A shocking incident at a wedding procession.",
                posterUrl = "https://image.tmdb.org/t/p/w500/kPZ85Wq5QzrBnZ5q5QzrBnZ5q5Q.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/kPZ85Wq5QzrBnZ5q5QzrBnZ5q5Q.jpg",
                contentType = ContentType.WEB_SERIES,
                genres = listOf("Crime", "Thriller", "Action"),
                languages = listOf("Hindi"),
                releaseYear = 2018,
                durationMinutes = null,
                rating = 4.7f,
                voteCount = 32000,
                isTrending = true,
                seasonCount = 3,
                episodeCount = 27,
                quality = VideoQuality.FULL_HD,
                ageRating = "A",
                cast = listOf("Pankaj Tripathi", "Ali Fazal"),
                streamUrls = mapOf("720p" to "https://example.com/mirzapur_720p.m3u8")
            ),
            Content(
                id = "5",
                title = "Attack on Titan",
                description = "Humanity lives behind walls to protect themselves from Titans.",
                posterUrl = "https://image.tmdb.org/t/p/w500/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
                backdropUrl = "https://image.tmdb.org/t/p/w1280/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
                contentType = ContentType.ANIME,
                genres = listOf("Action", "Fantasy", "Drama"),
                languages = listOf("Japanese", "Hindi", "English"),
                releaseYear = 2013,
                durationMinutes = null,
                rating = 4.9f,
                voteCount = 50000,
                isTrending = true,
                seasonCount = 4,
                episodeCount = 89,
                quality = VideoQuality.FULL_HD,
                ageRating = "A",
                cast = listOf("Yuki Kaji", "Yui Ishikawa"),
                streamUrls = mapOf("720p" to "https://example.com/aot_720p.m3u8")
            )
        )
    }

    private fun generateContinueWatching(): List<Content> {
        return generateTrendingContent().shuffled().take(5)
    }

    private fun generateRecommendedContent(): List<Content> {
        return generateTrendingContent().shuffled()
    }

    private fun generateFeaturedCollections(): List<FeaturedCollection> {
        val allContent = generateTrendingContent()
        return listOf(
            FeaturedCollection(
                id = "fc1",
                title = "Blockbuster Hits",
                description = "The biggest Indian blockbusters",
                content = allContent.filter { it.contentType == ContentType.MOVIE },
                bannerUrl = "https://example.com/banner_blockbusters.jpg"
            ),
            FeaturedCollection(
                id = "fc2",
                title = "Binge-Worthy Series",
                description = "Complete series to watch",
                content = allContent.filter { it.contentType == ContentType.WEB_SERIES },
                bannerUrl = "https://example.com/banner_webseries.jpg"
            ),
            FeaturedCollection(
                id = "fc3",
                title = "Anime Universe",
                description = "Popular anime series",
                content = allContent.filter { it.contentType == ContentType.ANIME },
                bannerUrl = "https://example.com/banner_anime.jpg"
            )
        )
    }

    private fun generateLiveChannels(): List<LiveChannel> {
        return listOf(
            LiveChannel("lc1", "ABP News", "https://example.com/abp_logo.png", "https://example.com/abp_live.m3u8", LiveTvCategory.NEWS, 101, currentProgram = "Breaking News"),
            LiveChannel("lc2", "Star Sports 1", "https://example.com/starsports1_logo.png", "https://example.com/starsports1_live.m3u8", LiveTvCategory.SPORTS, 201, currentProgram = "LIVE: Cricket"),
            LiveChannel("lc3", "Star Gold", "https://example.com/stargold_logo.png", "https://example.com/stargold_live.m3u8", LiveTvCategory.MOVIES, 301, currentProgram = "Movie: Pathaan"),
            LiveChannel("lc4", "MTV", "https://example.com/mtv_logo.png", "https://example.com/mtv_live.m3u8", LiveTvCategory.MUSIC, 401, currentProgram = "MTV Unplugged"),
            LiveChannel("lc5", "Nickelodeon", "https://example.com/nick_logo.png", "https://example.com/nick_live.m3u8", LiveTvCategory.KIDS, 501, currentProgram = "Doraemon")
        )
    }

    private fun generateWatchHistory(): List<WatchHistoryItem> {
        val allContent = generateTrendingContent()
        return allContent.take(3).mapIndexed { index, content ->
            WatchHistoryItem(
                contentId = content.id,
                content = content,
                watchedAt = System.currentTimeMillis() - (index * 86400000L),
                progress = (30..80).random(),
                episodeId = null
            )
        }
    }

    private fun generateDownloads(): List<DownloadItem> {
        return emptyList()
    }

    suspend fun toggleWatchlist(contentId: String) {
        val current = _watchlist.value.toMutableSet()
        if (contentId in current) {
            current.remove(contentId)
        } else {
            current.add(contentId)
        }
        _watchlist.value = current
    }

    suspend fun addToWatchHistory(item: WatchHistoryItem) {
        val current = _watchHistory.value.toMutableList()
        current.removeAll { it.contentId == item.contentId }
        current.add(0, item)
        _watchHistory.value = current
    }

    suspend fun clearWatchHistory() {
        _watchHistory.value = emptyList()
    }

    suspend fun addSearchQuery(query: String) {
        val current = _searchHistory.value.toMutableList()
        current.remove(query)
        current.add(0, query)
        if (current.size > 20) current.removeAt(current.size - 1)
        _searchHistory.value = current
    }

    suspend fun clearSearchHistory() {
        _searchHistory.value = emptyList()
    }

    suspend fun updateUserPreferences(preferences: UserPreferences) {
        _userPreferences.value = preferences
    }

    suspend fun addDownload(item: DownloadItem) {
        val current = _downloads.value.toMutableList()
        current.add(0, item)
        _downloads.value = current
    }

    suspend fun removeDownload(downloadId: String) {
        val current = _downloads.value.toMutableList()
        current.removeAll { it.id == downloadId }
        _downloads.value = current
    }

    suspend fun updateDownloadProgress(downloadId: String, progress: Int, status: DownloadStatus) {
        val current = _downloads.value.toMutableList()
        val index = current.indexOfFirst { it.id == downloadId }
        if (index != -1) {
            val item = current[index]
            current[index] = item.copy(progress = progress, status = status)
            _downloads.value = current
        }
    }
}
