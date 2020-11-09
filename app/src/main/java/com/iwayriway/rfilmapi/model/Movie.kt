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
):Parcelable