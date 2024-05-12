package com.example.movieappmad24.data

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.getMovies

class SeedDatabaseWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val database = MovieDatabase.getDatabase(context = applicationContext)
            val movieDao = database.movieDao()
            val repository = MovieRepository.getInstance(movieDao)
            val movies = getMovies()
            var movieId: Long = 1

            movies.forEach{ movie ->
                repository.addMovie(movie)
                println("MOVIE.image" + movie.images)
                movie.images.forEach{ imageUrl ->
                    repository.addMovieImage(MovieImage(movieId = movieId, url = imageUrl))
                }
                movieId++
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}