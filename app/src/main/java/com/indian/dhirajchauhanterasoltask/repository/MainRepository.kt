package com.indian.dhirajchauhanterasoltask.repository


import com.indian.dhirajchauhanterasoltask.api.RetrofitInstance
import com.indian.dhirajchauhanterasoltask.db.MainDatabase
import com.indian.dhirajchauhanterasoltask.model.Movies
import com.indian.dhirajchauhanterasoltask.model.MoviesItem

class MainRepository(
    val db: MainDatabase
) {

    suspend fun insertMovies(movies: List<MoviesItem>) = db.getMoviesDao().insert(movies)

    fun getSavedMovies() = db.getMoviesDao().getSavedMovies()

    fun getMoviesSortByDate() = db.getMoviesDao().getMoviesSortByDate()

    fun getMoviesSortByTitle() = db.getMoviesDao().getMoviesSortByTitle()

    suspend fun getMovies() =
        RetrofitInstance.api.getAllMovies()

}