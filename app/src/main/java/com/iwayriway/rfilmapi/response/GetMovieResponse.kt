package com.iwayriway.rfilmapi.response

import com.google.gson.annotations.SerializedName
import com.iwayriway.rfilmapi.model.Movie

data class GetMovieResponse (
    @SerializedName("page") val page: Int,
    @SerializedName("results") val movies: ArrayList<Movie>,
    @SerializedName("total_pages") val pages: Int
)