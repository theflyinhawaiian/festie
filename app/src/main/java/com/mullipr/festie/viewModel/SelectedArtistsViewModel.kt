package com.mullipr.festie.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mullipr.festie.model.Artist
import com.mullipr.festie.model.SelectedArtistsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SelectedArtistsViewModel(private val selectedArtists : List<Artist>) : ViewModel() {
    private val _uiState = MutableStateFlow(SelectedArtistsUiState(selectedArtists))
    val uiState = _uiState.asStateFlow()

    private val originalList = selectedArtists
    private val currentList = selectedArtists.toMutableList()

    fun artistSelected(artist : Artist){
        if(selectedArtists.find { it.id == artist.id } != null)
            currentList.remove(artist)
        else
            currentList.add(artist)

        artist.isSelected = !artist.isSelected
    }

    class Factory(private val selectedArtists : List<Artist>) : ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass : Class<T>) : T {
            return SelectedArtistsViewModel(selectedArtists) as T
        }
    }
}