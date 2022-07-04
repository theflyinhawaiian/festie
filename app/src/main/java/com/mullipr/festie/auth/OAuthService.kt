package com.mullipr.festie.auth

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.net.Uri
import com.mullipr.festie.BuildConfig
import net.openid.appauth.*

class OAuthService(ctx : Context) {
    private companion object {
        const val REFRESH_TOKEN_SHAREDPREFS_ENTRY = "spotify_refresh_token"
    }

    private val authService = AuthorizationService(ctx)
    private val sharedPreferences = ctx.getSharedPreferences("FestiePrefs", MODE_PRIVATE)

    fun getAuthIntent() : Intent {
        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://accounts.spotify.com/authorize"),
            Uri.parse("https://accounts.spotify.com/api/token")
        )

        val authRequest: AuthorizationRequest = AuthorizationRequest.Builder(
            serviceConfig,
            BuildConfig.SPOTIFY_CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse("com.mullipr.festie:/authorized")
        ).setScope("playlist-read-collaborative playlist-modify-public user-modify-playback-state user-read-playback-state user-read-currently-playing app-remote-control")
            .build()

        return authService.getAuthorizationRequestIntent(authRequest)
    }

    fun isUserAuthenticated() : Boolean {
        val token = sharedPreferences.getString(REFRESH_TOKEN_SHAREDPREFS_ENTRY, "")
        return token != ""
    }

    fun processAuthResponse(data : Intent?) : Boolean{
        if(data == null)
            return false

        val authResponse = AuthorizationResponse.fromIntent(data)
        val exception = AuthorizationException.fromIntent(data)

        if(authResponse == null)
            return false

        authService.performTokenRequest(authResponse.createTokenExchangeRequest()
        ) { response, _ ->
            if(response != null){
                val editor = sharedPreferences.edit()
                editor.putString(REFRESH_TOKEN_SHAREDPREFS_ENTRY, response.refreshToken)
                editor.apply()
            }
        }

        return true
    }

    fun invalidateRefreshToken() {
        val editor = sharedPreferences.edit()
        editor.putString(REFRESH_TOKEN_SHAREDPREFS_ENTRY, "")
        editor.apply()
    }
}