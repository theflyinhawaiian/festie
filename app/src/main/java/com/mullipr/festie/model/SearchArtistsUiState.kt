package com.mullipr.festie.model

data class SearchArtistsUiState(val artists : List<Artist>,
                                val isLoading : Boolean,
                                val selectedArtistsCount : Int)