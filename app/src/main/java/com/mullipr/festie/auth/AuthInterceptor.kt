package com.mullipr.festie.auth

import android.content.Context
import com.mullipr.festie.util.SharedPrefsWrapper
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(ctx : Context) : Interceptor {
    private val prefs = SharedPrefsWrapper(ctx)

    override fun intercept(chain : Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        prefs.getAccessToken().let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }

        return chain.proceed(requestBuilder.build())
    }
}