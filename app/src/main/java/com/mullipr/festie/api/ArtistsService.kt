package com.mullipr.festie.api

import android.util.Log
import com.mullipr.festie.api.endpoints.SearchResource
import com.mullipr.festie.model.Artist

// TODO: Have this return the result status and a potential error message if applicable
class ArtistsService(private val searchResource : SearchResource) {
    suspend fun search(searchText : String) : List<Artist> {
        val response = searchResource.search(searchText)

        if(!response.isSuccessful){
            Log.d("festie", "Error fetching artists: ${response.errorBody()}")
            return listOf()
        }

        val result = response.body()

        if(result == null || result.artists.items.isEmpty()){
            Log.d("festie", "No artists returned!")
            return listOf()
        }

        return response.body()!!.artists.items.map { artist -> artist.toInternal() }
    }
}