package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: MovieRepository) : ViewModel() {

    private val _selectedMovie = MutableStateFlow<MovieWithImages?>(null)
    val selectedMovie: StateFlow<MovieWithImages?> = _selectedMovie.asStateFlow()


    fun toggleFavoriteMovie(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        viewModelScope.launch {
            repository.updateMovie(movie)
        }
    }


    fun getMovieById(movieId: Long) {
        viewModelScope.launch {
           repository.getMovieById(movieId).collect() { movie ->
                _selectedMovie.value = movie
            }

        }
    }

}