package com.reabuilt.a94761525castlecustom.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.reabuilt.a94761525castlecustom.CastleApplication
import com.reabuilt.a94761525castlecustom.data.model.Content
import com.reabuilt.a94761525castlecustom.data.model.DownloadItem
import com.reabuilt.a94761525castlecustom.data.model.DownloadStatus
import com.reabuilt.a94761525castlecustom.data.model.FeaturedCollection
import com.reabuilt.a94761525castlecustom.data.model.LiveChannel
import com.reabuilt.a94761525castlecustom.data.model.ThemeMode
import com.reabuilt.a94761525castlecustom.data.model.UserPreferences
import com.reabuilt.a94761525castlecustom.data.model.VideoQuality
import com.reabuilt.a94761525castlecustom.data.model.WatchHistoryItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val application: CastleApplication) : ViewModel() {

    private val repository = application.contentRepository

    private val prefs: SharedPreferences =
        application.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val _onboardingCompleted = MutableStateFlow(prefs.getBoolean(KEY_ONBOARDING, false))
    val onboardingCompleted: StateFlow<Boolean> = _onboardingCompleted.asStateFlow()

    val preferences: StateFlow<UserPreferences> = repository.userPreferences
    val trendingContent: StateFlow<List<Content>> = repository.trendingContent
    val continueWatching: StateFlow<List<Content>> = repository.continueWatching
    val recommendedContent: StateFlow<List<Content>> = repository.recommendedContent
    val featuredCollections: StateFlow<List<FeaturedCollection>> = repository.featuredCollections
    val liveChannels: StateFlow<List<LiveChannel>> = repository.liveChannels
    val watchlist: StateFlow<Set<String>> = repository.watchlist
    val watchHistory: StateFlow<List<WatchHistoryItem>> = repository.watchHistory
    val downloads: StateFlow<List<DownloadItem>> = repository.downloads
    val searchHistory: StateFlow<List<String>> = repository.searchHistory

    private val runningDownloads = mutableMapOf<String, Job>()

    fun markOnboardingCompleted() {
        if (_onboardingCompleted.value) return
        _onboardingCompleted.value = true
        prefs.edit().putBoolean(KEY_ONBOARDING, true).apply()
    }

    fun toggleWatchlist(contentId: String) {
        viewModelScope.launch { repository.toggleWatchlist(contentId) }
    }

    fun updatePreferences(newPreferences: UserPreferences) {
        viewModelScope.launch { repository.updateUserPreferences(newPreferences) }
    }

    fun setVideoQuality(quality: VideoQuality) {
        updatePreferences(repository.userPreferences.value.copy(videoQuality = quality))
    }

    fun setThemeMode(mode: ThemeMode) {
        updatePreferences(repository.userPreferences.value.copy(themeMode = mode))
    }

    fun addSearchQuery(query: String) {
        val trimmed = query.trim()
        if (trimmed.isEmpty()) return
        viewModelScope.launch { repository.addSearchQuery(trimmed) }
    }

    fun clearSearchHistory() {
        viewModelScope.launch { repository.clearSearchHistory() }
    }

    fun clearWatchHistory() {
        viewModelScope.launch { repository.clearWatchHistory() }
    }

    fun getContent(contentId: String): Content? {
        val candidates = mutableListOf<Content>()
        candidates.addAll(repository.trendingContent.value)
        candidates.addAll(repository.continueWatching.value)
        candidates.addAll(repository.recommendedContent.value)
        candidates.addAll(repository.featuredCollections.value.flatMap { it.content })
        return candidates.firstOrNull { it.id == contentId }
    }

    fun getLiveChannel(channelId: String): LiveChannel? =
        repository.liveChannels.value.firstOrNull { it.id == channelId }

    fun allContent(): List<Content> {
        val base = mutableListOf<Content>()
        base.addAll(repository.trendingContent.value)
        base.addAll(repository.continueWatching.value)
        base.addAll(repository.recommendedContent.value)
        base.addAll(repository.featuredCollections.value.flatMap { it.content })
        return base.distinctBy { it.id }
    }

    fun startDownload(content: Content, episodeId: String? = null) {
        val alreadyPresent = repository.downloads.value.any {
            it.contentId == content.id &&
                it.episodeId == episodeId &&
                it.status != DownloadStatus.FAILED &&
                it.status != DownloadStatus.REMOVED
        }
        if (alreadyPresent) return

        val quality = when (repository.userPreferences.value.videoQuality) {
            VideoQuality.SD -> "480p"
            VideoQuality.HD -> "720p"
            VideoQuality.FULL_HD -> "1080p"
            VideoQuality.ULTRA_HD -> "2160p"
        }
        val item = DownloadItem(
            id = "dl_${content.id}_${episodeId ?: "main"}_${System.currentTimeMillis()}",
            contentId = content.id,
            content = content,
            episodeId = episodeId,
            downloadUrl = content.streamUrls.values.firstOrNull().orEmpty(),
            quality = quality,
            status = DownloadStatus.QUEUED,
            progress = 0,
            sizeBytes = 180_000_000L,
            fileSize = 180_000_000.0
        )
        viewModelScope.launch {
            repository.addDownload(item)
            startSimulation(item.id, 0)
        }
    }

    private fun startSimulation(downloadId: String, fromProgress: Int) {
        if (downloadId in runningDownloads) return
        runningDownloads[downloadId] = viewModelScope.launch {
            var progress = fromProgress
            while (progress < 100) {
                delay(600L)
                progress = (progress + (4..14).random()).coerceAtMost(100)
                repository.updateDownloadProgress(
                    downloadId,
                    progress,
                    if (progress >= 100) DownloadStatus.COMPLETED else DownloadStatus.DOWNLOADING
                )
            }
            runningDownloads.remove(downloadId)
        }
    }

    fun toggleDownloadPauseResume(downloadId: String) {
        val item = repository.downloads.value.firstOrNull { it.id == downloadId } ?: return
        when (item.status) {
            DownloadStatus.DOWNLOADING, DownloadStatus.QUEUED -> {
                runningDownloads[downloadId]?.cancel()
                runningDownloads.remove(downloadId)
                viewModelScope.launch {
                    repository.updateDownloadProgress(downloadId, item.progress, DownloadStatus.PAUSED)
                }
            }
            DownloadStatus.PAUSED -> {
                viewModelScope.launch {
                    repository.updateDownloadProgress(downloadId, item.progress, DownloadStatus.DOWNLOADING)
                }
                startSimulation(downloadId, item.progress)
            }
            else -> Unit
        }
    }

    fun removeDownload(downloadId: String) {
        runningDownloads[downloadId]?.cancel()
        runningDownloads.remove(downloadId)
        viewModelScope.launch { repository.removeDownload(downloadId) }
    }

    fun recordWatchProgress(contentId: String, progressPercent: Int) {
        val content = getContent(contentId) ?: return
        viewModelScope.launch {
            repository.addToWatchHistory(
                WatchHistoryItem(
                    contentId = contentId,
                    content = content,
                    watchedAt = System.currentTimeMillis(),
                    progress = progressPercent.coerceIn(0, 100)
                )
            )
        }
    }

    companion object {
        private const val PREFS_NAME = "castle_custom_prefs"
        private const val KEY_ONBOARDING = "onboarding_completed"
    }
}

class MainViewModelFactory(private val application: CastleApplication) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}

@Composable
fun rememberMainViewModel(): MainViewModel {
    val context = LocalContext.current
    val application = context.applicationContext as CastleApplication
    return viewModel<MainViewModel>(factory = MainViewModelFactory(application))
}
