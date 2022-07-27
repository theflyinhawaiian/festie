package com.mullipr.festie.api

import com.mullipr.festie.auth.IOAuthService
import com.mullipr.festie.util.SharedPrefsWrapper
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenAuthenticator @Inject constructor (private val prefs : SharedPrefsWrapper,
                                              private val oAuthService : IOAuthService) : Authenticator {
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