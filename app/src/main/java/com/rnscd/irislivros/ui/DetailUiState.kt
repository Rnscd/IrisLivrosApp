package com.rnscd.irislivros.ui

import com.rnscd.irislivros.data.network.Book

sealed interface DetailUiState{
        object Loading : DetailUiState
        object Error : DetailUiState
        data class Success(val book: Book) : DetailUiState
}