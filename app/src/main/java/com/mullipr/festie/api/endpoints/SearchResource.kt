package com.mullipr.festie.api.endpoints

import com.mullipr.festie.model.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface SearchResource {
    @GET("search?type=artist")
    suspend fun search(@Query(value="q", encoded=true) query: String?): Response<SearchResult>
}