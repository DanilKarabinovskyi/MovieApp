package com.example.movieapp.presentation.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.example.movieapp.R
import com.example.movieapp.core.components.ExpandableMovieCard
import com.example.movieapp.core.components.StandardToolbar
import com.example.movieapp.core.presentation.Screen
import com.example.movieapp.presentation.feed.MoviesScreenViewModel

@ExperimentalCoilApi
@Composable
fun FavouriteMoviesScreen(
    onNavigate: (String) -> Unit = {},
    onNavigateUp: () -> Unit = {},
    viewModel: MoviesScreenViewModel
) {
    val favouriteMovies = viewModel.favouriteMovies.value
    val expandedCardIds = viewModel.expandedCardIdsList
    Column(
        modifier = Modifier.fillMaxWidth().background(Color.White)
    ) {
        StandardToolbar(
            backgroundColor = Color(R.color.purple_200),
            onNavigateUp = onNavigateUp,
            title = {
                Text(
                    text = "Favourite Movies",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                )
            },
            modifier = Modifier.fillMaxWidth(),
            showBackArrow = true,

        )

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn {
                items(favouriteMovies.size) { i ->
                    val movie = favouriteMovies[i]
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
                        showFavoriteIcon = false
                    )
                }
            }
        }
    }
}