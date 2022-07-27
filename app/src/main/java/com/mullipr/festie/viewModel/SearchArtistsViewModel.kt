package com.mullipr.festie.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mullipr.festie.api.ArtistsService
import com.mullipr.festie.model.Artist
import com.mullipr.festie.model.SearchArtistsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchArtistsViewModel @Inject constructor(private val artistsService : ArtistsService) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchArtistsUiState(listOf(), false, 0))
    val uiState = _uiState.asStateFlow()

    val selectedArtists = mutableListOf<Artist>()

    fun restoreUiState(state : SearchArtistsUiState){
        selectedArtists.addAll(state.selectedArtists)
        _uiState.update {
            state
        }

    }
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
}