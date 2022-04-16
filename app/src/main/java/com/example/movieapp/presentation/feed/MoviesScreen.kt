package com.example.movieapp.presentation.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import coil.annotation.ExperimentalCoilApi
import com.example.movieapp.core.components.ExpandableMovieCard
import com.example.movieapp.core.components.StandardToolbar
import com.example.movieapp.core.presentation.Screen

@ExperimentalCoilApi
@Composable
fun MoviesScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: MoviesScreenViewModel
) {
    val movies = viewModel.movies.value
    val expandedCardIds = viewModel.expandedCardIdsList
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        StandardToolbar(
            backgroundColor = Color(com.example.movieapp.R.color.purple_200),
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = "Movies",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = false,
            navActions = {
                IconButton(onClick = {
                    onNavigate(Screen.FavouriteScreen.route)
                }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "",
                        tint = MaterialTheme.colors.onBackground
                    )
                }
            },
        )
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(movies.size) { i ->
                    val movie = movies[i]
                    ExpandableMovieCard(
                        cardModel = movie,
                        onCardArrowClick = { viewModel.onCardArrowClicked(movie.id) },
                        expanded = expandedCardIds.value.contains(movie.id),
                        onLikeClick = { it ->
                            viewModel.likeClick(it)
                        },
                        onItemClick = { it ->
                            onNavigate(Screen.MovieDetailScreen.route + "/$it")
                        },
                    )
                }
            }
        }
    }
}