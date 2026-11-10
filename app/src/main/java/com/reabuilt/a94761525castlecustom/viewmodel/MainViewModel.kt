package com.reabuilt.a94761525castlecustom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.reabuilt.a94761525castlecustom.CastleApplication
import com.reabuilt.a94761525castlecustom.data.model.Content
import com.reabuilt.a94761525castlecustom.data.model.FeaturedCollection
import com.reabuilt.a94761525castlecustom.data.model.LiveChannel
import com.reabuilt.a94761525castlecustom.data.model.UserPreferences
import com.reabuilt.a94761525castlecustom.data.model.WatchHistoryItem
import com.reabuilt.a94761525castlecustom.data.repository.ContentRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(application: CastleApplication) : ViewModel() {
    
    private val repository = ContentRepository(application)
    
    val trendingContent: StateFlow<List<Content>> = repository.trendingContent
    val continueWatching: StateFlow<List<Content>> = repository.continueWatching
    val recommendedContent: StateFlow<List<Content>> = repository.recommendedContent
    val featuredCollections: StateFlow<List<FeaturedCollection>> = repository.featuredCollections
    val liveChannels: StateFlow<List<LiveChannel>> = repository.liveChannels
    val watchlist: StateFlow<Set<String>> = repository.watchlist
    val watchHistory: StateFlow<List<WatchHistoryItem>> = repository.watchHistory
    val preferences: StateFlow<UserPreferences> = repository.userPreferences
    val searchHistory: StateFlow<List<String>> = repository.searchHistory
    
    fun toggleWatchlist(contentId: String) {
        viewModelScope.launch {
            repository.toggleWatchlist(contentId)
        }
    }
    
    fun addToWatchHistory(item: WatchHistoryItem) {
        viewModelScope.launch {
            repository.addToWatchHistory(item)
        }
    }
    
    fun updatePreferences(preferences: UserPreferences) {
        viewModelScope.launch {
            repository.updateUserPreferences(preferences)
        }
    }
    
    fun addSearchQuery(query: String) {
        viewModelScope.launch {
            repository.addSearchQuery(query)
        }
    }
    
    fun clearSearchHistory() {
        viewModelScope.launch {
            repository.clearSearchHistory()
        }
    }
}
