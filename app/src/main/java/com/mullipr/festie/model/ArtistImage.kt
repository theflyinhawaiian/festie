package com.mullipr.festie.model

import com.squareup.moshi.Json

data class ArtistImage(@field:Json(name="height") val height: Int,
                       @field:Json(name="width") val width: Int,
                       @field:Json(name="url") val url: String)