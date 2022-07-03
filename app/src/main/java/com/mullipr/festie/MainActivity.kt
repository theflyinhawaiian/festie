package com.mullipr.festie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import net.openid.appauth.*


class MainActivity : AppCompatActivity() {
    companion object {
        val RC_AUTH = 100;
    }

    lateinit var authService : AuthorizationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://accounts.spotify.com/authorize"),  // authorization endpoint
            Uri.parse("https://accounts.spotify.com/api/token")
        )

        val authRequest: AuthorizationRequest = AuthorizationRequest.Builder(
            serviceConfig,
            BuildConfig.SPOTIFY_CLIENT_ID,
            ResponseTypeValues.CODE,
            Uri.parse("com.mullipr.festie:/authorized")
        ).setScope("playlist-read-collaborative playlist-modify-public user-modify-playback-state user-read-playback-state user-read-currently-playing app-remote-control")
            .build()

        authService = AuthorizationService(this)
        val authIntent = authService.getAuthorizationRequestIntent(authRequest)

        startActivityForResult(authIntent, RC_AUTH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == RC_AUTH && data != null) {
            val authResponse = AuthorizationResponse.fromIntent(data)
            val exception = AuthorizationException.fromIntent(data)

            if(authResponse == null)
                return

            authService.performTokenRequest(authResponse.createTokenExchangeRequest()
            ) { response, _ ->
                if(response != null){
                    val time = System.currentTimeMillis()
                    Log.d("[festie]", "Current time: $time")
                    Log.d("[festie]", response.accessTokenExpirationTime.toString())
                    Toast.makeText(this, "WE HAVE A TOKEN: ${response.accessToken}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}