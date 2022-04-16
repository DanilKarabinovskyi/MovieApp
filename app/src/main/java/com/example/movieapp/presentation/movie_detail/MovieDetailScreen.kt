package com.example.movieapp.presentation.movie_detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.movieapp.R
import com.example.movieapp.core.components.ExpandableMovieCard
import com.example.movieapp.core.components.StandardToolbar
import com.example.movieapp.presentation.feed.MoviesScreenViewModel

@ExperimentalCoilApi
@Composable
fun MovieDetailScreen(
    onNavigateUp: () -> Unit = {},
    viewModel: MoviesScreenViewModel,
    movieId: String
) {
    val expandedCardIds = viewModel.expandedCardIdsList
    val movie = viewModel.getItem(movieId)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        StandardToolbar(
            backgroundColor = Color(R.color.purple_200),
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = movie?.title ?: "Movie Detail",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,
        )

        Column(modifier = Modifier.fillMaxSize()) {
            movie?.let {
                ExpandableMovieCard(
                    cardModel = movie,
                    onCardArrowClick = { viewModel.onCardArrowClicked(movie.id) },
                    expanded = expandedCardIds.value.contains(movie.id),
                    onLikeClick = { it ->
                        viewModel.likeClick(it)
                    },
                    onItemClick = {}
                )
                Row {
                    LazyRow {
                        items(movie.images.size) { i ->
                            Image(
                                painter = rememberImagePainter(
                                    data = movie.images[i],
                                    imageLoader = ImageLoader.invoke(LocalContext.current)
                                ),
                                contentDescription = "Image",
                                modifier = Modifier
                                    .height(250.dp)
                                    .width(250.dp)
                                    .padding(8.dp)
                                    .border(
                                        width = 1.dp,
                                        color = MaterialTheme.colors.onSurface,
                                    ),
                                contentScale = ContentScale.None
                            )
                        }
                    }
                }
            }
        }
    }
}