package com.mullipr.festie.model.dao

import com.mullipr.festie.model.Artist
import com.mullipr.festie.model.ArtistImage
import com.squareup.moshi.Json

data class ArtistDAO(@field:Json(name="genres") val genres : List<String>,
                  @field:Json(name="id") val id : String,
                  @field:Json(name="name") val name : String,
                  @field:Json(name="popularity") val popularity : Int,
                  @field:Json(name="images") val images : List<ArtistImage>) {

    fun toInternal() = Artist(genres, id, name, popularity, images.minByOrNull { i -> i.width }?.url ?: "")
}