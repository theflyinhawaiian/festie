package com.mullipr.festie.auth

import android.content.Intent

interface IOAuthService {
    fun getAuthIntent() : Intent
    suspend fun refreshAuthToken() : String?
    fun isUserAuthenticated() : Boolean
    fun processAuthResponse(data : Intent?, successCallback : () -> Unit)
    fun invalidateTokens()
}