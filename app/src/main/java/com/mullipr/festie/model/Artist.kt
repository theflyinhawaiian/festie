package com.mullipr.festie.model

import android.graphics.drawable.Drawable

data class Artist(val genres : List<String>,
                  val id : String,
                  val name : String,
                  val popularity : Int,
                  val image : ArtistImage?){
    var imageDrawable : Drawable? = null
    var isSelected : Boolean = false
}