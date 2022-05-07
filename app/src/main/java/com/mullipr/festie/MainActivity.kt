package com.mullipr.festie

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationServiceConfiguration
import net.openid.appauth.ResponseTypeValues


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://idp.example.com/auth"),  // authorization endpoint
            Uri.parse("https://idp.example.com/token")
        )

        val authRequestBuilder: AuthorizationRequest = AuthorizationRequest.Builder(
            serviceConfig,
            "9d2216a850c5414f9986f99f6e2f2907",
            ResponseTypeValues.CODE,
            Uri.parse("com.mullipr.festie:/authorized")
        ).setScope("playlist-read-collaborative playlist-modify-public user-modify-playback-state user-read-playback-state user-read-currently-playing app-remote-control")
            .build()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }
}