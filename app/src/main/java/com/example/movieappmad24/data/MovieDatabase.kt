package com.example.movieappmad24.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.movieappmad24.models.Movie
import com.example.movieappmad24.models.MovieImage

@Database(
    entities = [Movie::class, MovieImage::class],
    version = 1,
    exportSchema = false
)
abstract class MovieDatabase : RoomDatabase(){
    abstract fun movieDao(): MovieDao

    companion object {
        @Volatile
        private var instance: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(context, MovieDatabase::class.java, "movie_db")
                    .fallbackToDestructiveMigration()
                    .addCallback(callback = seedDatabase(context = context))
                    .build()
                    .also {
                        instance = it
                    }
            }
        }


        private fun seedDatabase(context: Context): Callback {
            return object : Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    println("hHELOOOOO")
                    super.onCreate(db)
                    val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
                    WorkManager.getInstance(context).enqueue(request)
                }
            }
        }

    }
}