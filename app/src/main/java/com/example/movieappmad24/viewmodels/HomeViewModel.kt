package com.example.movieappmad24.viewmodels

import android.util.Log
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

class HomeViewModel(private val repository: MovieRepository) : ViewModel(), MoviesViewModel {
    private val _movies = MutableStateFlow(listOf<MovieWithImages>())
    override val movies: StateFlow<List<MovieWithImages>> = _movies.asStateFlow()

    private val _favoriteMovies = MutableStateFlow(listOf<MovieWithImages>())
    val favoriteMovies: StateFlow<List<MovieWithImages>> = _favoriteMovies.asStateFlow()

    init {
        getAllMovies()
    }

    fun getAllMovies() {
        viewModelScope.launch {
            repository.getAllMovies().collect { movieList ->
                _movies.value = movieList
                Log.i("moviestest", movieList.toString())
            }
        }
        Log.i("moviestest", movies.value.toString())
    }

    override fun toggleFavoriteMovie(movie: Movie) {
        movie.isFavorite = !movie.isFavorite
        viewModelScope.launch {
            repository.updateMovie(movie)
        }
    }





}