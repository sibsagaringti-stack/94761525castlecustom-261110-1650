package com.reabuilt.a94761525castlecustom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reabuilt.a94761525castlecustom.CastleApplication
import com.reabuilt.a94761525castlecustom.data.model.UserPreferences
import com.reabuilt.a94761525castlecustom.data.repository.ContentRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: CastleApplication) : ViewModel() {

    private val contentRepository = ContentRepository(application)

    val preferences: StateFlow<UserPreferences> = contentRepository.userPreferences
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
