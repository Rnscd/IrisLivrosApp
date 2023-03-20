package com.rnscd.irislivros.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

//"maxResults=30"
interface BooksApi {

    @GET("volumes")
    suspend fun searchBooks(
        @Query("q") query: String = "kotlin",
        @Query("maxResults") maxResults: Int = 40
    ): Response<BooksDto>

    @GET("volumes/{id}")
    suspend fun getBook(
        @Path("id") id: String = "24yRRvkgsc8C"
    ): Response<Book>

}