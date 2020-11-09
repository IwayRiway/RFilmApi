package com.iwayriway.rfilmapi.response

import com.google.gson.annotations.SerializedName
import com.iwayriway.rfilmapi.model.Movie

data class GetMovieDetailResponse (
    @SerializedName("title") val title: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("vote_average") val vote_average: Double,
    @SerializedName("overview") val overview: String,
    @SerializedName("backdrop_path") val backdrop_path: String,
    @SerializedName("poster_path") val poster_path: String,

)