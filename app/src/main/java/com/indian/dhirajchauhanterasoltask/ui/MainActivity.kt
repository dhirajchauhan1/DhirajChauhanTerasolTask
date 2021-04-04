package com.indian.dhirajchauhanterasoltask.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.indian.dhirajchauhanterasoltask.databinding.ActivityMainBinding
import com.indian.dhirajchauhanterasoltask.db.MainDatabase

import com.indian.dhirajchauhanterasoltask.repository.MainRepository

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MainViewModel;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val repository = MainRepository(MainDatabase(this))
        val viewModelProviderFactory = ViewModelProviderFactory( application,repository)
        viewModel = ViewModelProvider(this,viewModelProviderFactory).get(MainViewModel::class.java)

    }
}