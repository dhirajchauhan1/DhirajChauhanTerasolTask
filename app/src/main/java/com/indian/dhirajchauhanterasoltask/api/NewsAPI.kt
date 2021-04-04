package com.indian.dhirajchauhanterasoltask.api


import com.indian.dhirajchauhanterasoltask.model.Movies
import retrofit2.Response
import retrofit2.http.GET


interface NewsAPI {

    @GET("moviedata.json")
    suspend fun getAllMovies(
    ): Response<Movies>

}