package com.indian.dhirajchauhanterasoltask.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.*
import android.net.NetworkCapabilities.*
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.indian.dhirajchauhanterasoltask.model.Movies
import com.indian.dhirajchauhanterasoltask.model.MoviesItem
import com.indian.dhirajchauhanterasoltask.repository.MainRepository
import com.indian.dhirajchauhanterasoltask.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException


class MainViewModel(
        application:Application,
    val repository: MainRepository
) : AndroidViewModel(application) {

    val movies: MutableLiveData<Resource<Movies>> = MutableLiveData()

    init {
       getMovies()
    }

    fun getMovies() = viewModelScope.launch {
        safeMoviesoCall()
    }
    private suspend fun safeMoviesoCall(){
        movies.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()){
                val response = repository.getMovies()
                movies.postValue(handleMoviesResponse(response))
            }
            else{
                movies.postValue(Resource.Error("No Internet Connection"))
            }
        }
        catch (t: Throwable){
            when(t){
                is IOException -> movies.postValue(Resource.Error("Network Api Failure $t"))
                else  -> movies.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    // we use google recommended resource class to handle api failure
    private fun handleMoviesResponse(response: Response<Movies>) : Resource<Movies>{
        if (response.isSuccessful){
            response.body()?.let { resultResponse ->
                saveMovies(resultResponse)
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    // save all movies to room database
    fun saveMovies(movies: List<MoviesItem>) = viewModelScope.launch {
        repository.insertMovies(movies)
    }

    fun getSavedMovies() = repository.getSavedMovies()

    fun getMoviesSortByDate() = repository.getMoviesSortByDate()

    fun getMoviesSortByTitle() = repository.getMoviesSortByTitle()

    private fun hasInternetConnection(): Boolean{
        val connectivityManager = getApplication<Application>().getSystemService(
                Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(TRANSPORT_WIFI) -> true
                capabilities.hasTransport(TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }
        else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    TYPE_WIFI -> true
                    TYPE_MOBILE-> true
                    TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}