package com.rnscd.irislivros.fake

import com.rnscd.irislivros.data.repository.BooksRepository

class FakeBooksRepository : BooksRepository {

    var returnNull = false
    var returnEmpty = false

    override suspend fun getBooksImages(query: String): List<Pair<String, String>>? {
        if (returnNull) return null
        if (returnEmpty) return emptyList()
        return FakeDataSource.images
    }
}