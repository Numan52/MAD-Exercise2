package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun insertMovie(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Query("SELECT * FROM movie WHERE id = :id")
    suspend fun loadMovieById(id: Long) : Movie

    @Query("SELECT * FROM movie")
    suspend fun loadAllMovies() : Flow<List<Movie>>

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    suspend fun loadAllFavoriteMovies() : Flow<List<Movie>>
}