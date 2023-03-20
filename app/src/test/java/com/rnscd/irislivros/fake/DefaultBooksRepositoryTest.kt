package com.rnscd.irislivros.fake

import com.rnscd.irislivros.data.repository.DefaultBooksRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultBooksRepositoryTest {

    @Test
    fun defaultBooksRepository_getBooksImages_verifyImagesList() = runTest {
        val fakeApi = FakeBooksApi().apply { hasBooks = true }
        val repository = DefaultBooksRepository(booksApi = fakeApi)
        assertEquals(FakeDataSource.images, repository.getBooksImages(""))
    }
}
