package com.mullipr.festie.model

import com.squareup.moshi.Json

data class PagedCollection<out T>(@field:Json(name="href") val href : String,
                                  @field:Json(name="items") val items : List<T>,
                                  @field:Json(name="limit") val limit : Int,
                                  @field:Json(name="next") val next : String,
                                  @field:Json(name="offset") val offset : Int,
                                  @field:Json(name="previous") val previous : String,
                                  @field:Json(name="total") val total : Int)