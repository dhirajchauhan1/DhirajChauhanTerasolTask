package com.indian.dhirajchauhanterasoltask.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.indian.dhirajchauhanterasoltask.model.MoviesItem

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(moviesItem: List<MoviesItem>)

    @Query("SELECT * FROM saved_movies")
    fun getSavedMovies(): LiveData<List<MoviesItem>>

    @Query("SELECT * FROM saved_movies ORDER BY year DESC")
    fun getMoviesSortByDate(): LiveData<List<MoviesItem>>

    @Query("SELECT * FROM saved_movies ORDER BY year ASC")
    fun getMoviesSortByTitle(): LiveData<List<MoviesItem>>

}