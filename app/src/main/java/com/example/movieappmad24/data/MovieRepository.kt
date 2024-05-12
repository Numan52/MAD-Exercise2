package com.example.movieappmad24.data

import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

class MovieRepository(private val movieDao: MovieDao) {
    suspend fun addMovie(movie: Movie) = movieDao.insertMovie(movie)
    suspend fun addMovieImage(movieImage: MovieImage) = movieDao.addImages(movieImage)
    suspend fun updateMovie(movie: Movie) = movieDao.updateMovie(movie)
    suspend fun deleteMovie(movie: Movie) = movieDao.deleteMovie(movie)
    suspend fun getAllMovies(): Flow<List<MovieWithImages>> = movieDao.loadAllMovies()
    suspend fun getFavoriteMovies(): Flow<List<MovieWithImages>> = movieDao.loadAllFavoriteMovies()
    suspend fun getMovieById(id: Long): Flow<MovieWithImages> = movieDao.loadMovieById(id)

    companion object {
        @Volatile
        private var Instance: MovieRepository? = null

        fun getInstance(dao: MovieDao) = Instance?: synchronized(this) {
            Instance ?: MovieRepository(dao).also { Instance = it }
        }
    }
}