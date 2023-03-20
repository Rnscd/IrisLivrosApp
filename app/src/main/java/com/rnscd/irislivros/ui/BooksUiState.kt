package com.rnscd.irislivros.ui

sealed interface BooksUiState {
    object Loading : BooksUiState
    object Error : BooksUiState
        data class Success(val books: List<Triple<String, String, String>>) : BooksUiState


}

