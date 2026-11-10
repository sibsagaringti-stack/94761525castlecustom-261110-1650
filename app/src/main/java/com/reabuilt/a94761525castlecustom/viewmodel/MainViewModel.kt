package com.reabuilt.a94761525castlecustom.viewmodel

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
import com.reabuilt.a94761525castlecustom.data.model.UserPreferences
import com.reabuilt.a94761525castlecustom.data.model.WatchHistoryItem
import com.reabuilt.a94761525castlecustom.data.repository.ContentRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ContentRepository) : ViewModel() {

    val preferences: StateFlow<UserPreferences> = repository.userPreferences
    val trendingContent: StateFlow<List<Content>> = repository.trendingContent
    val continueWatching: StateFlow<List<Content>> = repository.continueWatching
    val recommendedContent: StateFlow<List<Content>> = repository.recommendedContent
    val featuredCollections = repository.featuredCollections
    val liveChannels = repository.liveChannels
    val searchHistory = repository.searchHistory
    val watchlist = repository.watchlist
    val watchHistory = repository.watchHistory
    val downloads = repository.downloads

    val allContent: StateFlow<List<Content>> =
        combine(repository.trendingContent, repository.recommendedContent) { trending, recommended ->
            (trending + recommended).distinctBy { it.id }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun toggleWatchlist(contentId: String) {
        viewModelScope.launch { repository.toggleWatchlist(contentId) }
    }

    fun updatePreferences(newPreferences: UserPreferences) {
        viewModelScope.launch { repository.updateUserPreferences(newPreferences) }
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

    fun addDownload(content: Content) {
        val downloadId = "dl_${content.id}_${System.currentTimeMillis()}"
        viewModelScope.launch {
            repository.addDownload(
                DownloadItem(
                    id = downloadId,
                    content = content,
                    status = DownloadStatus.DOWNLOADING,
                    progress = 0
                )
            )
            for (progress in 10..100 step 10) {
                delay(350)
                repository.updateDownloadProgress(downloadId, progress, DownloadStatus.DOWNLOADING)
            }
            repository.updateDownloadProgress(downloadId, 100, DownloadStatus.COMPLETED)
        }
    }

    fun removeDownload(downloadId: String) {
        viewModelScope.launch { repository.removeDownload(downloadId) }
    }

    fun saveWatchProgress(contentId: String, progressPercent: Int, episodeId: String? = null) {
        viewModelScope.launch {
            val content = allContent.value.firstOrNull { it.id == contentId } ?: return@launch
            if (progressPercent <= 2) return@launch
            repository.addToWatchHistory(
                WatchHistoryItem(
                    contentId = contentId,
                    content = content,
                    watchedAt = System.currentTimeMillis(),
                    progress = progressPercent.coerceIn(0, 100),
                    episodeId = episodeId
                )
            )
        }
    }
}

class MainViewModelFactory(private val application: CastleApplication) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application.contentRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
    }
}

@Composable
fun localMainViewModel(): MainViewModel {
    val application = LocalContext.current.applicationContext as CastleApplication
    return viewModel(factory = MainViewModelFactory(application))
}
