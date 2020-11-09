package com.iwayriway.rfilmapi.response

import com.google.gson.annotations.SerializedName
import com.iwayriway.rfilmapi.model.Movie

data class GetMovieDetailResponse (
    @SerializedName("title") val title: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("overview") val overview: String

)