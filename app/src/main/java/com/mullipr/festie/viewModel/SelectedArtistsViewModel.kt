package com.mullipr.festie.viewModel

import androidx.lifecycle.ViewModel
import com.mullipr.festie.model.Artist
import com.mullipr.festie.model.SelectedArtistsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SelectedArtistsViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SelectedArtistsUiState(listOf(), listOf()))
    val uiState = _uiState.asStateFlow()

    val currentList = mutableListOf<Artist>()

    fun restoreUiState(state : SelectedArtistsUiState){
        currentList.addAll(state.selectedArtists)
        _uiState.update {
            state
        }
    }

    fun artistSelected(artist : Artist){
        if(currentList.find { it.id == artist.id } != null)
            currentList.remove(artist)
        else
            currentList.add(artist)

        artist.isSelected = !artist.isSelected
        _uiState.update {
            it.copy(selectedArtists = currentList, artistsCount = currentList.size)
        }
    }
}