package com.example.movieappmad24.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WatchlistViewModel(private val repository: MovieRepository) : ViewModel(), MoviesViewModel {

    private val _movies = MutableStateFlow(listOf<MovieWithImages>())
    override val movies: StateFlow<List<MovieWithImages>> = _movies.asStateFlow()


    init {
        viewModelScope.launch {
            repository.getFavoriteMovies().collect{ movies ->
                    _movies.value = movies
                }
        }
    }

    override fun toggleFavoriteMovie(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        viewModelScope.launch {
            repository.updateMovie(movie)
        }
    }



}