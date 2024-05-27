package com.example.movieapp.presenter.main.bottombar.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentHomeBinding
import com.example.movieapp.domain.model.Movie
import com.example.movieapp.presenter.main.bottombar.home.adapter.GenreMovieAdapter
import com.example.movieapp.presenter.model.GenrePresentation
import com.example.movieapp.util.StateView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private val viewModel: HomeViewModel by viewModels()

    private lateinit var genreMovieAdapter: GenreMovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        getGenres()
    }

    private fun getGenres() {
        viewModel.getGenres().observe(viewLifecycleOwner) { stateview ->
            when(stateview) {
                is StateView.Loading -> {

                }
                is StateView.Success -> {
                    val genres = stateview.data ?: emptyList()
                    genreMovieAdapter.submitList(genres)
                    getMoviesByGenre(genres)
                }
                is StateView.Error -> {
                    Log.i("TESTE", stateview.message.toString())
                }
            }
        }
    }

    private fun getMoviesByGenre(genres: List<GenrePresentation>) {
        val genreMutableList = genres.toMutableList()

        genreMutableList.forEachIndexed {index, genre ->
            viewModel.getMoviesByGenre(genre.id).observe(viewLifecycleOwner) { stateview ->
                when(stateview) {
                    is StateView.Loading -> {

                    }
                    is StateView.Success -> {
                        genreMutableList[index] = genre.copy(movies = stateview.data?.take(5))
                        lifecycleScope.launch {
                            delay(1000)
                            genreMovieAdapter.submitList(genreMutableList)
                        }
                    }
                    is StateView.Error -> {
                        Log.i("TESTE", stateview.message.toString())
                    }
                }
            }
        }
    }

    private fun initRecycler() {
        genreMovieAdapter = GenreMovieAdapter { genreId, name ->
            val action = HomeFragmentDirections.actionMenuHomeToMovieGenreFragment(genreId, name)
            findNavController().navigate(action)
        }

        with(binding!!.rvGenres) {
            setHasFixedSize(true)
            adapter = genreMovieAdapter
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}