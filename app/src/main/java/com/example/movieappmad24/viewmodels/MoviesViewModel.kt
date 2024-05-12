package com.example.movieappmad24.viewmodels

import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.StateFlow

interface MoviesViewModel {
    val movies: StateFlow<List<MovieWithImages>>
    fun toggleFavoriteMovie(movie: Movie)
}