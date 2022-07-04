package com.mullipr.festie.viewModel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import com.mullipr.festie.auth.OAuthService

class MainPageViewModel(application : Application) : AndroidViewModel(application) {
    private val oAuthService = OAuthService(application.applicationContext)

    fun invalidateAuthentication() = oAuthService.invalidateRefreshToken()

    fun isUserAuthenticated() : Boolean = oAuthService.isUserAuthenticated()

    fun getAuthIntent() : Intent = oAuthService.getAuthIntent()

    fun processAuthResponse(data : Intent?) : Boolean = oAuthService.processAuthResponse(data)
}