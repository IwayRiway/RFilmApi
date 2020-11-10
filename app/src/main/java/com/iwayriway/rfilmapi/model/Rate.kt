package com.iwayriway.rfilmapi.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Rate(
        @SerializedName("value")
        @Expose
        var value:Double?=0.0
):Parcelable