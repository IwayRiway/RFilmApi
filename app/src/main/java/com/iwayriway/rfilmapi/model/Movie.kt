package com.iwayriway.rfilmapi.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie (
    @SerializedName("id")
    @Expose
    var id:Int?=0,

    @SerializedName("title")
    var title:String?="" ,

    @SerializedName("poster_path")
    @Expose
    var poster_path:String?=""
//    @SerializedName("id") val id: Long,
//    @SerializedName("title") val title: String,
//    @SerializedName("overview") val overview: String,
//    @SerializedName("poster_path") val posterPath: String,
//    @SerializedName("backdrop_path") val backdropPath: String,
//    @SerializedName("vote_average") val rating: Float,
//    @SerializedName("release_date") val releaseDate: String
):Parcelable