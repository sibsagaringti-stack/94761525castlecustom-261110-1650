package com.reabuilt.a94761525castlecustom.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.reabuilt.a94761525castlecustom.CastleApplication
import com.reabuilt.a94761525castlecustom.data.model.UserPreferences
import com.reabuilt.a94761525castlecustom.data.repository.ContentRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ContentRepository) : ViewModel() {
    
    val preferences: StateFlow<UserPreferences> = repository.userPreferences
    val trendingContent = repository.trendingContent
    val continueWatching = repository.continueWatching
    val recommendedContent = repository.recommendedContent
    val featuredCollections = repository.featuredCollections
    val liveChannels = repository.liveChannels
    val watchlist = repository.watchlist
    val watchHistory = repository.watchHistory
    val downloads = repository.downloads
    val searchHistory = repository.searchHistory
    
    fun toggleWatchlist(contentId: String) {
        viewModelScope.launch {
            repository.toggleWatchlist(contentId)
        }
    }
    
    fun addToWatchHistory(item: com.reabuilt.a94761525castlecustom.data.repository.WatchHistoryItem) {
        viewModelScope.launch {
            repository.addToWatchHistory(item)
        }
    }
    
    fun addSearchQuery(query: String) {
        viewModelScope.launch {
            repository.addSearchQuery(query)
        }
    }
    
    fun updatePreferences(preferences: UserPreferences) {
        viewModelScope.launch {
            repository.updateUserPreferences(preferences)
        }
    }
}

class MainViewModelFactory(private val application: CastleApplication) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(ContentRepository(application)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
