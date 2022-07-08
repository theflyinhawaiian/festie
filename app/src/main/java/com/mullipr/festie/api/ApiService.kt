package com.mullipr.festie.api

import android.content.Context
import com.mullipr.festie.auth.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiService(baseURL : String,
                 ctx : Context) {
    companion object {
        val authURL = "https://accounts.spotify.com/"
        val baseURL = "https://api.spotify.com/v1/"
    }

    private var client : OkHttpClient
    private val retrofit : Retrofit

    init {
        val clientBuilder = OkHttpClient.Builder()

        if(baseURL == Companion.baseURL){
            clientBuilder
                .addInterceptor(AuthInterceptor(ctx))
        }

        client = clientBuilder.also {
            it.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }.build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseURL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }


    fun get() : Retrofit = retrofit
}