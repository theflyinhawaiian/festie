package com.mullipr.festie.auth

import com.mullipr.festie.util.SharedPrefsWrapper
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val prefs : SharedPrefsWrapper) : Interceptor {
    override fun intercept(chain : Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        prefs.getAccessToken().let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}