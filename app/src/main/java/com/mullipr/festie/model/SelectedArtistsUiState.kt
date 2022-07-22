package com.mullipr.festie.model

data class SelectedArtistsUiState(val startingList : List<Artist>,
                                  val selectedArtists : List<Artist>,
                                  var artistsCount : Int = 0)