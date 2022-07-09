package com.mullipr.festie.model

import com.mullipr.festie.model.dao.ArtistDAO
import com.squareup.moshi.Json

data class SearchResult(@field:Json(name="artists") val artists : PagedCollection<ArtistDAO>)