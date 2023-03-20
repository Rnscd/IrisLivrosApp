package com.rnscd.irislivros.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rnscd.irislivros.R


@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun BookshelfApp(
    viewModel: BooksViewModel = viewModel(factory = BooksViewModel.Factory)
) {
    val booksUiState = viewModel.booksUiState.collectAsStateWithLifecycle().value

    var bookId by rememberSaveable() { mutableStateOf("0") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            BookTopBar(onBooksSearch = viewModel::getBooksImages, onBack = { id -> bookId = id }, bookId)
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            if (bookId == "0") {
                when (booksUiState) {
                    BooksUiState.Error -> ErrorScreen(
                        onRefreshContent = { viewModel.getBooksImages() }
                    )
                    BooksUiState.Loading -> LoadingScreen()

                    is BooksUiState.Success -> LazyGridScreen(
                        books = booksUiState.books,
                        bookId = bookId,
                        onBookClick = { id -> bookId = id }
                    )
                }
            } else{
                viewModel.getBookInfo(bookId)
                BooksDetailScreen { id -> bookId = id }
            }
        }
    }
}

@Composable
fun LazyGridScreen(
    books: List<Triple<String, String, String>>,
    bookId: String,
    onBookClick: (String) -> Unit
) {
    Column {
        if (books.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = stringResource(R.string.no_results))
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(items = books,  key = { book -> book.third }) { book ->
                    PhotoCard(photo = book.second, title = book.first, bookId = book.third, onBookClick = onBookClick)
                }
            }
        }
    }
}
@Composable
fun BookTopBar(onBooksSearch: (String) -> Unit, onBack: (String) -> Unit, bookId: String) {
    val focusManager = LocalFocusManager.current
    var query by rememberSaveable { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 3.dp,)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (bookId != "0") {
                IconButton(
                    onClick = { onBack("0") },
                    modifier = Modifier.padding(end = 4.dp)
                ) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "back")
                }
            }
            Text(
                text = "Iris Livros",
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 30.dp, start = if (bookId != "0") 0.dp else 50.dp)
            )
        }

        TextField(
            value = query,
            onValueChange = { query = it },
            singleLine = true,
            shape = RoundedCornerShape(40.dp),
            placeholder = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    Text(text = stringResource(R.string.search), modifier = Modifier.padding(start = 24.dp))
            },
            colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),

            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    onBooksSearch(query)
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 2.dp)
        )
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(
    onRefreshContent: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_connection_error),
                contentDescription = stringResource(R.string.connection_error)
            )
            IconButton(onClick = onRefreshContent) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = stringResource(R.string.refresh)
                )
            }
        }
    }
}

@Composable
fun PhotoCard(photo: String, title: String, modifier: Modifier = Modifier, bookId: String, onBookClick: (String) -> Unit) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(270.dp)
            .clickable { onBookClick(bookId) },
        elevation = 4.dp,
    ) {
        Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(photo)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = "Photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = title,
                fontSize = 15.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}
@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewBook() {
     BookshelfApp(
        viewModel = viewModel(factory = BooksViewModel.Factory)
    )
}