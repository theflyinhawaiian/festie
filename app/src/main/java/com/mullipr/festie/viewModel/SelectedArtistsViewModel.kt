package com.mullipr.festie.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mullipr.festie.model.Artist
import com.mullipr.festie.model.SelectedArtistsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SelectedArtistsViewModel(private val selectedArtists : List<Artist>) : ViewModel() {
    private val _uiState = MutableStateFlow(SelectedArtistsUiState(selectedArtists,selectedArtists))
    val uiState = _uiState.asStateFlow()

    val currentList = selectedArtists.toMutableList()

    fun artistSelected(artist : Artist){
        if(currentList.find { it.id == artist.id } != null)
            currentList.remove(artist)
        else
            currentList.add(artist)

        artist.isSelected = !artist.isSelected
        _uiState.update {
            it.copy(selectedArtists = currentList, artistsCount = currentList.count())
        }
    }

    class Factory(private val selectedArtists : List<Artist>) : ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass : Class<T>) : T {
            return SelectedArtistsViewModel(selectedArtists) as T
        }
    }
}