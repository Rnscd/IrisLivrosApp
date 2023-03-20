package com.rnscd.irislivros.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rnscd.irislivros.data.network.VolumeInfo
import com.rnscd.irislivros.R

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun BooksDetailScreen(onBack: (String) -> Unit) {

    val viewModel: BooksViewModel = viewModel(factory = BooksViewModel.Factory)

    val detailUiState = viewModel.detailUiState.collectAsStateWithLifecycle().value

    when (detailUiState) {
        DetailUiState.Error -> ErrorScreen(
            onRefreshContent = {  }
        )
        DetailUiState.Loading -> LoadingScreen()

        is DetailUiState.Success -> (
               DetailsColumn(volumeInfo = detailUiState.book.volumeInfo)
                )
    }
}

@Composable
fun DetailsColumn(volumeInfo: VolumeInfo) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .verticalScroll(state = rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(Modifier.padding(horizontal = 4.dp, vertical = 16.dp)) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(volumeInfo.imageLinks?.httpsThumbnail)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = "Photo",
                modifier = Modifier
                    .height(220.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column() {
                Text(text = volumeInfo.title,
                    maxLines = 4,
                    fontSize = 18.sp,
                    overflow = TextOverflow.Ellipsis
                )

                volumeInfo.authors?.let { authors ->
                    for (author in authors) {
                        Text(text = author, fontSize = 17.sp,
                            maxLines = 4,
                            overflow = TextOverflow.Ellipsis,
                                    color = Color.Gray
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(4.dp))

                volumeInfo.pageCount?.let {
                    Text(
                        text = "Páginas: $it",
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                }
                volumeInfo.language?.let {
                    Text(
                        text = "Lingua: $it",
                        fontSize = 15.sp,
                        color = Color.Gray

                    )
                }

            }
        }
        volumeInfo.description?.let {
            Text(
            text = it,
                textAlign = TextAlign.Justify,
                fontSize = 18.sp,
            modifier = Modifier
                .padding(2.dp)
            )
        }
    }
}

