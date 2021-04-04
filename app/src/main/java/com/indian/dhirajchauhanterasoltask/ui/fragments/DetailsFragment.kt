package com.indian.dhirajchauhanterasoltask.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.indian.dhirajchauhanterasoltask.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    val args: DetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)

        val moviesItem = args.moviesItem
        activity?.let { Glide.with(it).load(moviesItem.info.image_url).into(binding.imageView) }
        binding.title.text = "${moviesItem.title}  ${moviesItem.year} "
        binding.ratingBar.rating = moviesItem.info.rating.toFloat()
        binding.numberOfRating.text = moviesItem.info.rating.toFloat().toString()
        binding.description.text = moviesItem.info.plot
        binding.actors.text = moviesItem.info.actors.toString()

       // val commaSeperatedString = moviesItem.info.actors.joinToString (separator = ", ")


        return binding.root
    }

}