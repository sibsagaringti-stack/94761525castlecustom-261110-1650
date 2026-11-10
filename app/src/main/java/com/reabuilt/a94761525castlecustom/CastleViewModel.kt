package com.reabuilt.a94761525castlecustom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.reabuilt.a94761525castlecustom.data.model.UserPreferences
import com.reabuilt.a94761525castlecustom.data.repository.ContentRepository
import kotlinx.coroutines.flow.StateFlow

class CastleViewModel(
    private val application: CastleApplication
) : ViewModel() {

    private val contentRepository = ContentRepository(application)

    val userPreferences: StateFlow<UserPreferences> = contentRepository.userPreferences
    val trendingContent = contentRepository.trendingContent
    val continueWatching = contentRepository.continueWatching
    val recommendedContent = contentRepository.recommendedContent
    val featuredCollections = contentRepository.featuredCollections
    val liveChannels = contentRepository.liveChannels
    val watchlist = contentRepository.watchlist
    val watchHistory = contentRepository.watchHistory
    val downloads = contentRepository.downloads
    val searchHistory = contentRepository.searchHistory

    fun toggleWatchlist(contentId: String) {
        viewModelScope.launch {
            contentRepository.toggleWatchlist(contentId)
        }
    }

    fun updatePreferences(preferences: UserPreferences) {
        viewModelScope.launch {
            contentRepository.updateUserPreferences(preferences)
        }
    }
}

class CastleViewModelFactory(
    private val application: CastleApplication
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CastleViewModel::class.java)) {
            return CastleViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
