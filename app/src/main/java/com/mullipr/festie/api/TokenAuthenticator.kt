package com.mullipr.festie.api

import android.content.Context
import com.mullipr.festie.auth.OAuthService
import com.mullipr.festie.util.SharedPrefsWrapper
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(ctx: Context) : Authenticator {
    private val prefs = SharedPrefsWrapper(ctx)
    private val oAuthService = OAuthService(ctx)

    override fun authenticate(route: Route?, response: Response): Request? {
        var success : Boolean = false
        return runBlocking {
            val token = oAuthService.refreshAuthToken() ?: return@runBlocking null

            return@runBlocking response.request.newBuilder()
                .header("Authorization", "Bearer ${prefs.getAccessToken()}")
                .build()
        }
    }
}