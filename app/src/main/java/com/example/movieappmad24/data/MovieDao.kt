package com.example.movieappmad24.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage
import com.example.movieappmad24.models.MovieWithImages
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert
    suspend fun insertMovie(movie: Movie)

    @Insert
    suspend fun addImages(movieImage: MovieImage)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Update
    suspend fun updateMovie(movie: Movie)

    @Query("SELECT * FROM movie WHERE dbId = :id")
    fun loadMovieById(id: Long) : Flow<MovieWithImages>

    @Query("SELECT * FROM movie")
    fun loadAllMovies() : Flow<List<MovieWithImages>>

    @Query("SELECT * FROM movie WHERE isFavorite = 1")
    fun loadAllFavoriteMovies() : Flow<List<MovieWithImages>>
}