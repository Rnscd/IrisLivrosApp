package com.rnscd.irislivros.fake

import com.rnscd.irislivros.data.network.Book
import com.rnscd.irislivros.data.network.BooksDto
import com.rnscd.irislivros.data.network.Thumbnails
import com.rnscd.irislivros.data.network.VolumeInfo

object FakeDataSource {

    val books = listOf(
        Book(
            "1",
            VolumeInfo(Thumbnails("url1"), "title1",1,
                "description1", "language1", listOf<String>("author1, author2")
            )
        ),
        Book(
            "2",
            VolumeInfo(Thumbnails("url2"), "title2",2,
                "description2", "language2", listOf<String>("author3, author4")
            )
        )
    )

    val dto = BooksDto(books)

    val dtoNoBooks = BooksDto(null)

    val images: List<Triple<String, String, String>> = books.mapNotNull {
        val titles = it.volumeInfo.title
        val pics = it.volumeInfo.imageLinks!!.httpsThumbnail
        val id = it.id
        Triple(titles, pics, id)}

    /*
      data.books?.let { books ->
                        books.mapNotNull { book ->
                            val title = book.volumeInfo.title
                            val thumbnail = book.volumeInfo.imageLinks?.httpsThumbnail
                            if (title != null && thumbnail != null) {
                                Pair(title, thumbnail)
     */
}