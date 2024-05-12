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
import com.example.movieappmad24.data.MovieDatabase
import com.example.movieappmad24.data.MovieRepository
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
    detailViewModel: DetailViewModel
) {
    detailViewModel.getMovieById(movieId)
    val selectedMovie by detailViewModel.selectedMovie.collectAsState()

    selectedMovie?.let { movie ->
        Scaffold (
            topBar = {
                SimpleTopAppBar(title = movie.movie.title) {
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
                    movieWithImages = movie,
                    onFavoriteClick = { movie ->
                        detailViewModel.toggleFavoriteMovie(movie)
                    })

                MovieTrailer(movieTrailer = movie.movie.trailer)
                HorizontalScrollableImageView(movieWithImages = movie)
            }
        }
    }
}




