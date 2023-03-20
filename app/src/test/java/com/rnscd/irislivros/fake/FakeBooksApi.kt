package com.rnscd.irislivros.fake

import com.rnscd.irislivros.data.network.Book
import com.rnscd.irislivros.data.network.BooksApi
import com.rnscd.irislivros.data.network.BooksDto
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.BufferedSource
import okio.IOException
import retrofit2.Response

class FakeBooksApi : BooksApi {

    var hasBooks = true
    var throwsException = false
    var errorResponse = false

     suspend fun searchBooks(query: String): Response<BooksDto> {
        if (throwsException) throw IOException()
        if (errorResponse) {
            return Response.error(404, object : ResponseBody() {
                override fun contentLength(): Long {
                    return 0L
                }

                override fun contentType(): MediaType? {
                    return null
                }

                override fun source(): BufferedSource {
                    TODO("Not yet implemented")
                }
            })
        }
        return if (hasBooks) {
            Response.success(FakeDataSource.dto)
        } else {
            Response.success(FakeDataSource.dtoNoBooks)
        }
    }

    override suspend fun searchBooks(query: String, maxResults: Int): Response<BooksDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getBook(query: String): Response<Book> {
        return Response.success(FakeDataSource.books.first())
    }
}