package com.mullipr.festie.viewModel

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mullipr.festie.api.ArtistsService
import com.mullipr.festie.api.endpoints.SearchResource
import com.mullipr.festie.model.Artist
import com.mullipr.festie.model.SearchArtistsUiState
import com.mullipr.festie.util.DrawableUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class SearchArtistsViewModel(searchResource : SearchResource) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchArtistsUiState(listOf(), false, 0))
    val uiState = _uiState.asStateFlow()

    private val artistsService = ArtistsService(searchResource)

    private val selectedArtists = mutableListOf<Artist>()

    fun searchArtists(text : String?) {
        if(text == null)
            return

        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val artists = artistsService.search(text)

            for(artist in artists){
                if(artist.image?.url != null)
                    artist.imageDrawable = DrawableUtils.fromUrl(artist.image.url)
                else {
                    val r = Random()
                    artist.imageDrawable = ColorDrawable(Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256)))
                }

                artist.isSelected = artist in selectedArtists
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
        if(artist in selectedArtists)
            selectedArtists.remove(artist)
        else
            selectedArtists.add(artist)

        artist.isSelected = !artist.isSelected

        _uiState.update {
            it.copy(selectedArtistsCount = selectedArtists.size)
        }
    }

    class Factory(private val searchResource : SearchResource) : ViewModelProvider.Factory{
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass : Class<T>) : T {
            return SearchArtistsViewModel(searchResource) as T
        }
    }
}