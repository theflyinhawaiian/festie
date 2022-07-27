package com.mullipr.festie.api

import com.mullipr.festie.BuildConfig
import com.mullipr.festie.auth.AuthInterceptor
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class ApiService @Inject constructor(tokenAuthenticator: TokenAuthenticator,
                                     authInterceptor: AuthInterceptor) {
    companion object {
        const val baseUrl = "https://api.spotify.com/v1/"
    }

    private var client : OkHttpClient
    private val retrofit : Retrofit

    init {
        client = getClientBuilder(tokenAuthenticator)
            .addInterceptor(authInterceptor)
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