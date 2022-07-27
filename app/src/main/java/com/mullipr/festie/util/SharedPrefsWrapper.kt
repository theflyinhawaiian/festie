package com.mullipr.festie.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class SharedPrefsWrapper @Inject constructor(@ApplicationContext ctx : Context) {
    private companion object {
        const val REFRESH_TOKEN_ENTRY = "spotify_refresh_token"
        const val ACCESS_TOKEN_ENTRY = "spotify_access_token"
    }

    private val userPrefs = ctx.getSharedPreferences("FestiePrefs", MODE_PRIVATE)

    fun saveAccessTokens(accessToken : String, refreshToken : String){
        val editor = userPrefs.edit()
        editor.putString(ACCESS_TOKEN_ENTRY, accessToken)
        editor.putString(REFRESH_TOKEN_ENTRY, refreshToken)
        editor.apply()
    }

    fun invalidateAccessTokens(){
        val editor = userPrefs.edit()
        editor.putString(REFRESH_TOKEN_ENTRY, "")
        editor.putString(ACCESS_TOKEN_ENTRY, "")
        editor.apply()
    }

    fun hasAccessTokens() : Boolean =
        userPrefs.getString(REFRESH_TOKEN_ENTRY, "") != ""
                && userPrefs.getString(ACCESS_TOKEN_ENTRY, "") != ""

    fun getRefreshToken() : String = userPrefs.getString(REFRESH_TOKEN_ENTRY, "")!!

    fun getAccessToken() : String = userPrefs.getString(ACCESS_TOKEN_ENTRY, "")!!
}