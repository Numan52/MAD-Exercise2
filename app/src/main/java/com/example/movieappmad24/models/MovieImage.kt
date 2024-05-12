package com.example.movieappmad24.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieImage(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val movieId: Long,
    val url: String
)
