package com.mullipr.festie.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mullipr.festie.api.ArtistsService
import com.mullipr.festie.api.endpoints.SearchResource
import com.mullipr.festie.model.SearchArtistsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchArtistsViewModel(searchResource : SearchResource) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchArtistsUiState(listOf(), false, listOf()))
    val uiState = _uiState.asStateFlow()

    private val artistsService = ArtistsService(searchResource)

    fun fetchArtists() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val artists = artistsService.search("madeon")

            _uiState.update {
                it.copy(
                    isLoading = false,
                    artists = artists
                )
            }
        }
    }

    class Factory(private val searchResource : SearchResource) : ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass : Class<T>) : T {
            return SearchArtistsViewModel(searchResource) as T
        }
    }
}