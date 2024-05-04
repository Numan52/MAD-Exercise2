package com.example.movieappmad24.viewmodels

import android.adservices.adid.AdId
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

// Inherit from ViewModel class
class MoviesViewModel(private val repository: MovieRepository) : ViewModel() {
    private val _movies = MutableStateFlow(listOf<Movie>())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    private val _favoriteMovies = MutableStateFlow(listOf<Movie>())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie.asStateFlow()

    init {
        getAllMovies()
    }

    fun getAllMovies() {
        viewModelScope.launch {
            repository.getAllMovies().collect { movieList ->
                _movies.value = movieList
            }
        }
    }

    fun toggleFavoriteMovie(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        viewModelScope.launch {
            repository.updateMovie(movie)
        }
    }

    fun getFavoriteMovies() {
        viewModelScope.launch {
            repository.getFavoriteMovies().collect { movieList ->
                _favoriteMovies.value = movieList
            }
        }
    }

    fun getMovieById(movieId: Long) {
        viewModelScope.launch {
            val movie = repository.getMovieById(movieId)
            _selectedMovie.value = movie
        }
    }

    // rest of logic
}