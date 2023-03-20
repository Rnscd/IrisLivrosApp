package com.rnscd.irislivros.di

import com.rnscd.irislivros.data.network.BooksApi
import com.rnscd.irislivros.data.repository.BooksRepository

interface AppContainer {
    val booksApi: BooksApi
    val booksRepository: BooksRepository
}

