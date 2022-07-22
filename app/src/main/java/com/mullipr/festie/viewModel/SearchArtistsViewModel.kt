package com.mullipr.festie.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mullipr.festie.api.ArtistsService
import com.mullipr.festie.api.endpoints.SearchResource
import com.mullipr.festie.model.Artist
import com.mullipr.festie.model.SearchArtistsUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchArtistsViewModel(searchResource : SearchResource, artists : List<Artist>) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchArtistsUiState(listOf(), false, artists.count()))
    val uiState = _uiState.asStateFlow()

    private val artistsService = ArtistsService(searchResource)

    val selectedArtists = artists.toMutableList()

    fun searchArtists(text : String?) {
        if(text == null)
            return

        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val artists = artistsService.search(text)

            for(artist in artists){
                artist.isSelected = selectedArtists.find { it.id == artist.id } != null
            }

            _uiState.update {
                it.copy(
                    isLoading = false,
                    artists = artists
                )
            }
        }
    }

    fun artistSelected(artist : Artist){
        if(selectedArtists.find { it.id == artist.id } != null)
            selectedArtists.remove(artist)
        else
            selectedArtists.add(artist)

        artist.isSelected = !artist.isSelected

        _uiState.update {
            it.copy(selectedArtistsCount = selectedArtists.size)
        }
    }

    class Factory(private val searchResource : SearchResource,
                  private val artists : List<Artist>) : ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass : Class<T>) : T {
            return SearchArtistsViewModel(searchResource, artists) as T
        }
    }
}