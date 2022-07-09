package com.mullipr.festie.model

data class Artist(val genres : List<String>,
                  val id : String,
                  val name : String,
                  val popularity : Int,
                  val image : ArtistImage?)