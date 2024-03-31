package com.example.movieappmad24.navigation

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.movieappmad24.screens.DetailScreen
import com.example.movieappmad24.screens.HomeScreen
import com.example.movieappmad24.screens.Screen
import com.example.movieappmad24.screens.WatchlistScreen

const val MOVIE_ID = "movieId"

@Composable
fun Navigation() {
    val navController = rememberNavController() // create a NavController instance


    NavHost(navController = navController, // pass the NavController to NavHost
        startDestination = Screen.Home.route) {  // pass a start destination

        composable(
            route = Screen.Home.route
        ) {   // route with name "homescreen" navigates to HomeScreen composable
            HomeScreen(navController = navController)
        }

        composable(
            route = Screen.Details.route + "/{$MOVIE_ID}",
            arguments = listOf(navArgument(MOVIE_ID) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            DetailScreen(movieId = backStackEntry.arguments?.getString(MOVIE_ID), navController)
        }
        
        composable(
            route = Screen.Watchlist.route
        ) {
            WatchlistScreen(navController = navController)
        }
    }
}