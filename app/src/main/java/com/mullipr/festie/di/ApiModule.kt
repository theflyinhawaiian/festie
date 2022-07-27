package com.mullipr.festie.di

import com.mullipr.festie.api.ApiService
import com.mullipr.festie.api.endpoints.SearchResource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
abstract class ApiModule {
    companion object {
        @Provides fun bindRetrofit(apiService : ApiService) : Retrofit = apiService.get()
        @Provides fun bindSearchResource(retrofit : Retrofit) : SearchResource = retrofit.create(SearchResource::class.java)
    }
}