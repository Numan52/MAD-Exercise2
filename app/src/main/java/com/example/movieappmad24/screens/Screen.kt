package com.example.movieappmad24.screens


sealed class Screen(val route: String) {
    object Home: Screen(route = "homeScreen")
    object Details: Screen(route = "detailsScreen")
    object Watchlist: Screen(route = "watchlist")

}
