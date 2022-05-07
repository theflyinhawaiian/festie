package com.mullipr.festie

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("display_name")
    var displayName: String)