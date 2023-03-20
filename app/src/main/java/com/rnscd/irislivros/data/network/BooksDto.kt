package com.rnscd.irislivros.data.network

import com.google.gson.annotations.SerializedName

data class BooksDto(
    @SerializedName("items") val books: List<Book>?
)