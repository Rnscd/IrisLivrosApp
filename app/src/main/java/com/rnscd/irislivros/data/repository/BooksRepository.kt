package com.rnscd.irislivros.data.repository

import com.rnscd.irislivros.data.network.Book

interface BooksRepository {
    suspend fun getBooksImages(query: String): List<Triple<String, String, String>>?

    suspend fun getBookInfo(id: String): Book?
}