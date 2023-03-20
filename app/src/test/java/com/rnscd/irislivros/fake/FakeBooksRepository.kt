package com.rnscd.irislivros.fake

import com.rnscd.irislivros.data.network.Book
import com.rnscd.irislivros.data.repository.BooksRepository

class FakeBooksRepository : BooksRepository {

    var returnNull = false
    var returnEmpty = false

    override suspend fun getBooksImages(query: String): List<Triple<String, String, String>>? {
        if (returnNull) return null
        if (returnEmpty) return emptyList()
        return FakeDataSource.images
    }

    override suspend fun getBookInfo(id: String): Book? {
        if (returnNull) return null
        return FakeDataSource.dtoNoBooks.books?.get(0)
    }
}