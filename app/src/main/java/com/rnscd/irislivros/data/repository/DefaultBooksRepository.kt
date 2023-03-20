package com.rnscd.irislivros.data.repository

import com.rnscd.irislivros.data.network.Book
import com.rnscd.irislivros.data.network.BooksApi
import com.rnscd.irislivros.data.network.VolumeInfo

class DefaultBooksRepository(
    private val booksApi: BooksApi
) : BooksRepository {

    override suspend fun getBooksImages(query: String):  List<Triple<String, String, String>>?  {
        return try {
            val response = booksApi.searchBooks(query)
            if (response.isSuccessful) {
                val data = response.body()!!
                data.books?.let { books ->
                        books.mapNotNull { book ->
                            val title = book.volumeInfo.title
                            val thumbnail = book.volumeInfo.imageLinks?.httpsThumbnail
                            val id = book.id
                            if (title != null && thumbnail != null) {
                                Triple(title, thumbnail, id)
                            } else {
                                null
                            }
                        }
                } ?: emptyList()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    override suspend fun getBookInfo(id: String): Book? {
        return try{
            val response = booksApi.getBook(id)
            if (response.isSuccessful){
                val data = response.body()!!
                data.volumeInfo.let { volumeInfo ->
                    val title = volumeInfo.title
                    val thumbnail = volumeInfo.imageLinks
                    val pageCount = volumeInfo.pageCount
                    val description = volumeInfo.description
                    val language = volumeInfo.language
                    val authors = volumeInfo.authors


                    val volumeInfo: VolumeInfo = VolumeInfo(
                        thumbnail, title, pageCount, description, language , authors)

                    if (volumeInfo != null) {
                        Book("null", volumeInfo = volumeInfo)
                    } else {
                        null
                    }
                }
            } else{
                null
            }
        } catch (e:Exception) {
            e.printStackTrace()
            null
        }
    }
}