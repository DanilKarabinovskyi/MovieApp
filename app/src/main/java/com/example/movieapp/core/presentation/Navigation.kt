package com.example.movieapp.core.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.movieapp.presentation.favorite.FavouriteMoviesScreen
import com.example.movieapp.presentation.feed.MoviesScreen
import com.example.movieapp.presentation.feed.MoviesScreenViewModel
import com.example.movieapp.presentation.movie_detail.MovieDetailScreen

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalMaterialApi
@Composable
fun Navigation(
    navController: NavHostController,
    cardsViewModel: MoviesScreenViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.MoviesFeed.route,
        modifier = Modifier.fillMaxSize(),

        ) {
        composable(Screen.MoviesFeed.route) {
            MoviesScreen(
                onNavigate = navController::navigate,
                onNavigateUp = {},
                viewModel = cardsViewModel
            )
        }
        composable(Screen.FavouriteScreen.route) {
            FavouriteMoviesScreen(
                onNavigate = navController::navigate,
                onNavigateUp = {navController.popBackStack()},
                viewModel = cardsViewModel
            )
        }
        composable(
            route = Screen.MovieDetailScreen.route + "/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.StringType
                },
            )
        ) {
            val movieId = it.arguments?.getString("movieId")!!
            MovieDetailScreen(
                onNavigateUp = navController::navigateUp,
                movieId = movieId,
                viewModel = cardsViewModel
            )
        }
    }
}