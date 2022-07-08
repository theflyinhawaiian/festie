package com.mullipr.festie.auth

import com.squareup.moshi.Json

data class RefreshTokenRequest(
    @field:Json(name="grant_type") val grantType : String,
    @field:Json(name="refresh_token") val refreshToken : String,
    @field:Json(name="client_id") val clientId : String
)