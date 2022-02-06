package com.peter.flickrsearch.framework.datasource.responses

import com.google.gson.annotations.SerializedName

data class PhotoResponse(
    @SerializedName("id")
    val id: String?,

    @SerializedName("owner")
    val owner: String?,

    @SerializedName("secret")
    val secret: String?,

    @SerializedName("server")
    val server: String?,

    @SerializedName("farm")
    val farm: String?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("isFriend")
    val isFriend: Int?,

    @SerializedName("isPublic")
    val isPublic: Int?,

    @SerializedName("isFamily")
    val isFamily: Int?,
)
