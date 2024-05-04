package com.example.movieappmad24.widgets

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.movieappmad24.R
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.getMovies
import com.example.movieappmad24.navigation.Screen
import com.example.movieappmad24.viewmodels.MoviesViewModel
import java.lang.Exception


@Composable
fun MovieList(
    modifier: Modifier,
    movies: List<Movie>,
    navController: NavController,
    viewModel: MoviesViewModel
){
    LazyColumn(modifier = modifier) {
        items(movies) { movie ->
            MovieRow(
                movie = movie,
                onFavoriteClick = {movieId ->
                    viewModel.toggleFavoriteMovie(movieId)
                },
                onItemClick = { movieId ->
                    navController.navigate(route = Screen.DetailScreen.withId(movieId))
                }
            )
        }
    }
}

@Composable
fun MovieRow(
    modifier: Modifier = Modifier,
    movie: Movie,
    onFavoriteClick: (Long) -> Unit = {},
    onItemClick: (Long) -> Unit = {}
){
    Card(modifier = modifier
        .fillMaxWidth()
        .padding(5.dp)
        .clickable {
            onItemClick(movie.id)
        },
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column {

            MovieCardHeader(
                imageUrl = movie.images[0],
                isFavorite = movie.isFavorite,
                onFavoriteClick = { onFavoriteClick(movie.id) }
            )

            MovieDetails(modifier = modifier.padding(12.dp), movie = movie)

        }
    }
}

@Composable
fun MovieCardHeader(
    imageUrl: String,
    isFavorite: Boolean = false,
    onFavoriteClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .height(150.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {

        MovieImage(imageUrl)

        FavoriteIcon(isFavorite = isFavorite, onFavoriteClick)
    }
}

@Composable
fun MovieImage(imageUrl: String){
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        contentScale = ContentScale.Crop,
        contentDescription = "movie poster",
        loading = {
            CircularProgressIndicator()
        }
    )
}


@Composable
fun MovieTrailer(movieTrailer: String) {
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    val context = LocalContext.current

    val mediaItem = MediaItem.fromUri(
        "android.resource://${context.packageName}/${movieTrailer}"
    )

    println("mediaId ${movieTrailer}")

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }
    println("exoPlayer.mediaItemCount: ${exoPlayer.mediaItemCount}")

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            exoPlayer.release()
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    AndroidView(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(18f / 9f),
        factory = {
            PlayerView(context).also { playerView ->
                playerView.player = exoPlayer
            }
        },
        update = {
            when(lifecycle) {

                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()

                }
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.pause()
                }
                else -> Unit
            }
        }
    )
}

@Composable
fun FavoriteIcon(
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit = {}
) {
    println(isFavorite)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopEnd
    ){
        Icon(
            modifier = Modifier.clickable {
                onFavoriteClick() },
            tint = MaterialTheme.colorScheme.secondary,
            imageVector =
            if (isFavorite) {
                Icons.Filled.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },

            contentDescription = "Add to favorites")
    }
}


@Composable
fun MovieDetails(modifier: Modifier, movie: Movie) {
    var showDetails by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = movie.title)
        Icon(modifier = Modifier
            .clickable {
                showDetails = !showDetails
            },
            imageVector =
            if (showDetails) Icons.Filled.KeyboardArrowDown
            else Icons.Default.KeyboardArrowUp, contentDescription = "show more")
    }


    AnimatedVisibility(
        visible = showDetails,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        Column (modifier = modifier) {
            Text(text = "Director: ${movie.director}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Released: ${movie.year}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Genre: ${movie.genre}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Actors: ${movie.actors}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Rating: ${movie.rating}", style = MaterialTheme.typography.bodySmall)

            Divider(modifier = Modifier.padding(3.dp))

            Text(buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 13.sp)) {
                    append("Plot: ")
                }
                withStyle(style = SpanStyle(color = Color.DarkGray, fontSize = 13.sp, fontWeight = FontWeight.Normal)){
                    append(movie.plot)
                }
            })
        }
    }
}


@Composable
fun HorizontalScrollableImageView(movie: Movie) {
    LazyRow {
        items(movie.images) { image ->
            Card(
                modifier = Modifier
                    .padding(12.dp)
                    .size(240.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {

                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie poster",
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}