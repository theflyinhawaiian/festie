package com.mullipr.festie.auth

import android.content.Intent
import android.net.Uri
import com.mullipr.festie.BuildConfig
import com.mullipr.festie.util.SharedPrefsWrapper
import net.openid.appauth.*
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class OAuthService @Inject constructor(private val authService : AuthorizationService,
                                       private val sharedPreferences : SharedPrefsWrapper) : IOAuthService {

    private val serviceConfig = AuthorizationServiceConfiguration(
        Uri.parse("https://accounts.spotify.com/authorize"),
        Uri.parse("https://accounts.spotify.com/api/token")
    )

    override fun getAuthIntent() : Intent {
        val authRequest: AuthorizationRequest = AuthorizationRequest.Builder(
            serviceConfig,
            BuildConfig.SPOTIFY_CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse("com.mullipr.festie:/authorized")
        ).setScope("playlist-read-collaborative playlist-modify-public user-modify-playback-state user-read-playback-state user-read-currently-playing app-remote-control")
            .build()

        return authService.getAuthorizationRequestIntent(authRequest)
    }

    override suspend fun refreshAuthToken() : String? {
        return suspendCoroutine { continuation ->
            val refreshToken = sharedPreferences.getRefreshToken()
            val tokenRequest =
                TokenRequest.Builder(serviceConfig, BuildConfig.SPOTIFY_CLIENT_ID)
                    .setGrantType(GrantTypeValues.REFRESH_TOKEN)
                    .setRefreshToken(refreshToken)
                    .build()

            authService.performTokenRequest(tokenRequest) { response, exception ->
                if (response != null && exception == null) {
                    sharedPreferences.saveAccessTokens(response.accessToken!!,
                        response.refreshToken!!)
                    continuation.resume(response.accessToken!!)
                } else {
                    continuation.resume(null)
                }
            }
        }
    }

    override fun isUserAuthenticated() : Boolean = sharedPreferences.hasAccessTokens()

    override fun processAuthResponse(data : Intent?, successCallback : () -> Unit){
        if(data == null)
            return

        val authResponse = AuthorizationResponse.fromIntent(data)
        val exception = AuthorizationException.fromIntent(data)

        if(authResponse == null)
            return

        authService.performTokenRequest(authResponse.createTokenExchangeRequest()
        ) { response, _ ->
            if(response?.accessToken != null && response.refreshToken != null){
                sharedPreferences.saveAccessTokens(response.accessToken!!, response.refreshToken!!)
                successCallback()
            }
        }

        return
    }

    override fun invalidateTokens() {
        sharedPreferences.invalidateAccessTokens()
    }
}