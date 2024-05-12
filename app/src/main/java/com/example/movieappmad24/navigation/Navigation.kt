package com.example.movieappmad24.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.di.InjectorUtils
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.WatchlistScreen
import com.example.movieappmad24.viewmodels.DetailViewModel
import com.example.movieappmad24.viewmodels.HomeViewModel
import com.example.movieappmad24.viewmodels.WatchlistViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController() // create a NavController instance

    val homeViewModel: HomeViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))
    val detailViewModel: DetailViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))
    val watchlistViewModel: WatchlistViewModel = viewModel(factory = InjectorUtils.provideMovieViewModelFactory(context = LocalContext.current))

    NavHost(navController = navController, // pass the NavController to NavHost
        startDestination = Screen.HomeScreen.route) {  // pass a start destination
        composable(route = Screen.HomeScreen.route){   // route with name "homescreen" navigates to HomeScreen composable
            HomeScreen(navController = navController, homeViewModel = homeViewModel)
        }

        composable(
            route = Screen.DetailScreen.route,
            arguments = listOf(navArgument(name = DETAIL_ARGUMENT_KEY) {type = NavType.LongType})
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong(DETAIL_ARGUMENT_KEY)?.let {
                DetailScreen(
                    navController = navController,
                    movieId = it,
                    detailViewModel = detailViewModel
                )
            }
        }

        composable(route = Screen.WatchlistScreen.route){
            WatchlistScreen(navController = navController, watchlistViewModel = watchlistViewModel)
        }
    }
}