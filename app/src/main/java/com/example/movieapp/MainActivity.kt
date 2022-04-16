package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import com.example.movieapp.core.presentation.Navigation
import com.example.movieapp.presentation.feed.MoviesScreen
import com.example.movieapp.presentation.feed.MoviesScreenViewModel
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {

    private val cardsViewModel by viewModels<MoviesScreenViewModel>()

    @OptIn(ExperimentalCoilApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class,
        androidx.compose.material.ExperimentalMaterialApi::class
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val scaffoldState = rememberScaffoldState()
            MovieAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Navigation(
                        navController,
                        cardsViewModel = cardsViewModel)
                }
            }
        }
    }
}
