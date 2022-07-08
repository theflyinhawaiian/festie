package com.mullipr.festie.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mullipr.festie.api.endpoints.SearchResource
import kotlinx.coroutines.launch

class SearchArtistsViewModel(private val searchResource : SearchResource) : ViewModel() {

    fun fetchArtists() {
        viewModelScope.launch {
            val response = searchResource.search("madeon")

            if(!response.isSuccessful){
                Log.d("festie", "Error fetching artists: ${response.errorBody()}")
                return@launch
            }

            val result = response.body()

            if(result == null || result.artists.items.count() == 0){
                Log.d("festie", "No artists returned!")
                return@launch
            }

            for(artist in result.artists.items){
                Log.d("festie", "Found artist: $artist")
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