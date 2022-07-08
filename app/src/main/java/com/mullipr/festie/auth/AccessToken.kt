package com.mullipr.festie.auth

import com.squareup.moshi.Json

data class AccessToken(@field:Json(name="access_token") val token : String)