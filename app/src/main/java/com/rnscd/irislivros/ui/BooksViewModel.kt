package com.rnscd.irislivros.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.rnscd.irislivros.BookshelfApplication
import com.rnscd.irislivros.data.repository.BooksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val _booksUiState = MutableStateFlow<BooksUiState>(BooksUiState.Loading)
    val booksUiState = _booksUiState.asStateFlow()

    private val _detailUiState = MutableStateFlow<DetailUiState>(DetailUiState.Loading)
    val detailUiState = _detailUiState.asStateFlow()

    init {
        getBooksImages()
    }

    fun getBooksImages(query: String = "Android") {
        if (query.isEmpty()) return
        viewModelScope.launch {
            _booksUiState.value = BooksUiState.Loading
            val result = booksRepository.getBooksImages(query)
            _booksUiState.value = result?.let {
                BooksUiState.Success(it)
            } ?: BooksUiState.Error
        }
    }

    fun getBookInfo(id: String = "0"){
        viewModelScope.launch {
            _detailUiState.value = DetailUiState.Loading
            val result = booksRepository.getBookInfo(id)
            _detailUiState.value = result?.let {
                DetailUiState.Success(it)
            } ?: DetailUiState.Error
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as BookshelfApplication)
                val booksRepository = application.container.booksRepository
                BooksViewModel(booksRepository = booksRepository)
            }
        }
    }
}