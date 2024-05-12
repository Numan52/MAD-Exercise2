package com.example.movieappmad24.di

import android.content.Context
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.viewmodels.MoviesViewModelFactory

object InjectorUtils {

    private fun getMoviesRepository(context: Context): MovieRepository {
        return MovieRepository.getInstance(MovieDatabase.getDatabase(context.applicationContext).movieDao())
    }
    fun provideMovieViewModelFactory(context: Context) : MoviesViewModelFactory {
        val repository = getMoviesRepository(context)
        return MoviesViewModelFactory(repository = repository)
    }
}