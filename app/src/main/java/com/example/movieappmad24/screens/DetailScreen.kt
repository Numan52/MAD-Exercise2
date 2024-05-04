package com.example.movieappmad24.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieappmad24.R
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.viewmodels.DetailViewModel
import com.example.movieappmad24.viewmodels.MoviesViewModel
import com.example.movieappmad24.viewmodels.MoviesViewModelFactory
import com.example.movieappmad24.widgets.HorizontalScrollableImageView
import com.example.movieappmad24.widgets.MovieRow
import com.example.movieappmad24.widgets.MovieTrailer
import com.example.movieappmad24.widgets.SimpleTopAppBar

@Composable
fun DetailScreen(
    movieId: Long,
    navController: NavController,
    moviesViewModel: MoviesViewModel
) {
    val db = MovieDatabase.getDatabase(LocalContext.current)
    val repository = MovieRepository(movieDao = db.movieDao())
    val factory = MoviesViewModelFactory(repository = repository)
    val viewModel: DetailViewModel = viewModel(factory = factory)

    viewModel.getMovieById(movieId)
    val movie by viewModel.selectedMovie.collectAsState()

        if (movie != null) {
            Scaffold (
                topBar = {
                    SimpleTopAppBar(title = movie!!.title) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Go back"
                            )
                        }
                    }
                }
            ){ innerPadding ->
                Column {
                    MovieRow(
                        modifier = Modifier.padding(innerPadding),
                        movie = movie!!,
                        onFavoriteClick = { movie ->
                            moviesViewModel.toggleFavoriteMovie(movie)
                        })
                    MovieTrailer(movieTrailer = movie!!.trailer)
                    HorizontalScrollableImageView(movie = movie!!)
                }
            }
        }

    }

