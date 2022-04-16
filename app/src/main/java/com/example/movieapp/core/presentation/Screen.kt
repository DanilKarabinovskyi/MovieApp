package com.example.movieapp.core.presentation

sealed class Screen(val route: String) {
    object MoviesFeed : Screen("main_feed_screen")
    object MovieDetailScreen : Screen("post_detail_screen")
    object FavouriteScreen : Screen("chat_screen")
}
