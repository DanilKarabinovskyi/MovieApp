package com.example.movieapp.presentation.feed

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.entity.Movie
import com.example.movieapp.data.entity.getMovies
import com.example.movieapp.data.entity.moviesList

class MoviesScreenViewModel : ViewModel() {

    private val _movies = mutableStateOf(listOf<Movie>())
    val movies: State<List<Movie>> = _movies

    private val _favouriteMovies = mutableStateOf(listOf<Movie>())
    val favouriteMovies: State<List<Movie>> = _favouriteMovies

    private val _expandedCardIdsList = mutableStateOf(listOf<String>())
    val expandedCardIdsList: State<List<String>> get() = _expandedCardIdsList

    var likedSet = setOf<String>()

    init {
        _movies.value = getMovies()
    }

    fun onCardArrowClicked(cardId: String) {
        _expandedCardIdsList.value = _expandedCardIdsList.value.toMutableList().also { list ->
            if (list.contains(cardId)) list.remove(cardId) else list.add(cardId)
        }
    }

    fun likeClick(id: String) {
        likedSet = if (likedSet.contains(id)) {
            likedSet.minus(id)
        } else {
            likedSet.plus(id)
        }

        val list = mutableListOf<Movie>()
        val likedList = mutableListOf<Movie>()

        getMovies().forEach {
            if (likedSet.contains(it.id)) {
                list.add(it.copy(liked = true))
                likedList.add(it.copy(liked = true))
            } else {
                list.add(it.copy(liked = false))
            }
        }
        _favouriteMovies.value = likedList
        _movies.value = list
        moviesList = list
    }

    fun getItem(id: String): Movie? {
        getMovies().forEach {
            if (id == it.id) return it
        }
        return null
    }
}