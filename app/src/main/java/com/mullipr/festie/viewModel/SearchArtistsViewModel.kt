package com.mullipr.festie.viewModel

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mullipr.festie.api.ArtistsService
import com.mullipr.festie.api.endpoints.SearchResource
import com.mullipr.festie.model.SearchArtistsUiState
import com.mullipr.festie.util.DrawableUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.*

class SearchArtistsViewModel(searchResource : SearchResource) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchArtistsUiState(listOf(), false, listOf()))
    val uiState = _uiState.asStateFlow()

    private val artistsService = ArtistsService(searchResource)

    fun fetchArtists() {
        _uiState.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch(Dispatchers.IO) {
            val artists = artistsService.search("madeon")

            for(artist in artists){
                if(artist.image?.url != null)
                    artist.imageDrawable = DrawableUtils.fromUrl(artist.image.url)
                else {
                    val r = Random()
                    artist.imageDrawable = ColorDrawable(Color.argb(255, r.nextInt(256), r.nextInt(256), r.nextInt(256)))
                }
            }

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