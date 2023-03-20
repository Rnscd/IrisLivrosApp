package com.rnscd.irislivros.data.network

import com.google.gson.annotations.SerializedName

data class VolumeInfo(

    @SerializedName("imageLinks") val imageLinks: Thumbnails?,
    @SerializedName("title") val title: String,
    @SerializedName("pageCount") val pageCount: Int?,
    @SerializedName("description") val description: String?,
    @SerializedName("language") val language: String?,
    @SerializedName("authors") val authors: List<String>?,

    )