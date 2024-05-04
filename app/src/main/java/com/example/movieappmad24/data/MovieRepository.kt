package com.example.movieappmad24.data

import com.example.movieappmad24.models.Movie
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    suspend fun addMovie(movie: Movie) = movieDao.insertMovie(movie)
    suspend fun updateMovie(movie: Movie) = movieDao.updateMovie(movie)
    suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie)
    suspend fun getAllMovies(): Flow<List<Movie>> = movieDao.loadAllMovies()
    suspend fun getFavoriteMovies(): Flow<List<Movie>> = movieDao.loadAllFavoriteMovies()
    suspend fun getMovieById(id: Long): Movie = movieDao.loadMovieById(id)
}