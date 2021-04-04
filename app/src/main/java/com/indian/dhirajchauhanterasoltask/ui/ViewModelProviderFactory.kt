package com.indian.dhirajchauhanterasoltask.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.indian.dhirajchauhanterasoltask.repository.MainRepository


class ViewModelProviderFactory(
        val application: Application,
        val repository: MainRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(application, repository) as T
    }
}