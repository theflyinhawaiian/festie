package com.mullipr.festie.model

import com.squareup.moshi.Json

data class Artist(@field:Json(name="genres") val genres : List<String>,
                  @field:Json(name="id") val id : String,
                  @field:Json(name="name") val name : String,
                  @field:Json(name="popularity") val popularity : Int)