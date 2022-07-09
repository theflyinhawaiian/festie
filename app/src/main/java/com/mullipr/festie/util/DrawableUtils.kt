package com.mullipr.festie.util

import android.graphics.drawable.Drawable
import java.io.InputStream
import java.net.URL

class DrawableUtils {
    companion object {
        fun fromUrl (url : String?) : Drawable?{
            return try {
                val `is`: InputStream = URL(url).content as InputStream
                Drawable.createFromStream(`is`, "src name")
            } catch (e: Exception) {
                null
            }
        }
    }
}