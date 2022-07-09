package com.mullipr.festie.api

import android.content.Context
import com.mullipr.festie.BuildConfig
import com.mullipr.festie.auth.AuthInterceptor
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class ApiService(ctx : Context) {
    companion object {
        const val baseUrl = "https://api.spotify.com/v1/"
    }

    private var client : OkHttpClient
    private val retrofit : Retrofit

    init {
        val authenticator = TokenAuthenticator(ctx)

        client = getClientBuilder(authenticator)
            .addInterceptor(AuthInterceptor(ctx))
            .build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun getClientBuilder(authenticator : Authenticator? = null) =
        OkHttpClient.Builder()
            .also {
                client ->
                authenticator?.let { client.authenticator(it) }
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }


    fun get() : Retrofit = retrofit
}