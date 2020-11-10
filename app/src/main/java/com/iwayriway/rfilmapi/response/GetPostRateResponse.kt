package com.iwayriway.rfilmapi.response

import com.google.gson.annotations.SerializedName

data class GetPostRateResponse (
        @SerializedName("success") val success: Boolean,
        @SerializedName("status_message") val status_message: String
)