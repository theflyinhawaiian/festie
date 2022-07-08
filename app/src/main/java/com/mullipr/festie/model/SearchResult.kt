package com.mullipr.festie.model

import com.squareup.moshi.Json

data class SearchResult(@field:Json(name="artists") val artists : PagedCollection<Artist>)