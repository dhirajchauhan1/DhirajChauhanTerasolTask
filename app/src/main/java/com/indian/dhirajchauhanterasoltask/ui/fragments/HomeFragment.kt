package com.indian.dhirajchauhanterasoltask.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.AbsListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.indian.dhirajchauhanterasoltask.R
import com.indian.dhirajchauhanterasoltask.adapter.MoviesAdapter
import com.indian.dhirajchauhanterasoltask.databinding.FragmentHomeBinding
import com.indian.dhirajchauhanterasoltask.ui.MainActivity
import com.indian.dhirajchauhanterasoltask.ui.MainViewModel
import com.indian.dhirajchauhanterasoltask.util.Resource


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: MainViewModel
    lateinit var moviesAdapter: MoviesAdapter

    val TAG = "HomeFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setUpRecyclerView()

        viewModel = (activity as MainActivity).viewModel
        viewModel.movies.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
               /* is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { moviesResponse ->
                        viewModel.saveMovies(moviesResponse)
                        //trendingPhotoAdapter.differ.submitList(photoResponse.photos.toList())
                    }
                }
*/
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Toast.makeText(
                            activity as MainActivity,
                            "An Error Occured : $message",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.e(TAG, "An Error Occured : $message")
                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }

            }
        })

        showProgressBar()
        viewModel.getSavedMovies().observe(viewLifecycleOwner, Observer { moviesList ->
            moviesAdapter.differ.submitList(moviesList.toList())
            Log.e(TAG, Gson().toJson(moviesList)+"hwvd")
            hideProgressBar()
        })

        binding.toolbar.inflateMenu(R.menu.sort_menu)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {

                R.id.year -> {
                    showProgressBar()
                    viewModel.getMoviesSortByDate().observe(viewLifecycleOwner, Observer { moviesList ->
                        moviesAdapter.differ.submitList(null)
                        moviesAdapter.differ.submitList(moviesList.toList())
                        Log.e(TAG, Gson().toJson(moviesList)+"hwvd")
                        hideProgressBar()
                    })
                    Toast.makeText(activity as MainActivity, "Sorting by Year", Toast.LENGTH_LONG).show()
                }

                R.id.title -> {
                    showProgressBar()
                    viewModel.getMoviesSortByTitle().observe(viewLifecycleOwner, Observer { moviesList ->
                        moviesAdapter.differ.submitList(null)
                        moviesAdapter.differ.submitList(moviesList.toList())
                        Log.e(TAG, Gson().toJson(moviesList)+"hwvd")
                        hideProgressBar()
                    })
                    Toast.makeText(activity as MainActivity, "Sorting by Title", Toast.LENGTH_LONG).show()
                }

            }
            true
        }

        return binding.root
    }



    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
        Log.d(TAG, "Loading")
    }

    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun setUpRecyclerView(){
        moviesAdapter = MoviesAdapter(activity?.applicationContext!!)
        binding.moviesRecycler.apply {
            adapter = moviesAdapter
        }

        moviesAdapter.onItemClick = {
            /*val intent1 = Intent(activity, ResultActivity::class.java)
            intent1.putExtra("category_name", category.name)
            startActivity(intent1)*/

            val bundle = Bundle().apply {
                putSerializable("moviesItem", it)
            }

            findNavController().navigate(
                R.id.action_homeFragment_to_detailsFragment,
                bundle
            )

            Toast.makeText(
                activity as MainActivity,
                "Clicked",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}