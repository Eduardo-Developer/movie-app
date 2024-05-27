package com.example.movieapp.presenter.main.moviegenre

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.movieapp.R
import com.example.movieapp.databinding.FragmentMovieGenreBinding
import com.example.movieapp.presenter.main.bottombar.home.adapter.MovieAdapter
import com.example.movieapp.util.StateView
import com.example.movieapp.util.hideKeyboard
import com.example.movieapp.util.initToolbar
import com.ferfalk.simplesearchview.SimpleSearchView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieGenreFragment : Fragment() {

    private var _binding: FragmentMovieGenreBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieGenreViewModel by viewModels()
    private val args: MovieGenreFragmentArgs by navArgs()

    private lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieGenreBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.i("INFOTESTE", "OnviewCreated: ${args.genreid}")

        initToolbar(binding.toolbar)
        binding.toolbar.title = args.name
        initRecycler()

        getMoviesByGenre()
        initSearchView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search_view, menu)
        val item = menu.findItem(R.id.action_search)
        binding.simpleSearchView.setMenuItem(item)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun getMoviesByGenre() {
        viewModel.getMoviesByGenre(args.genreid).observe(viewLifecycleOwner) { stateview ->
            when (stateview) {
                is StateView.Loading -> {
                    binding.recyclerMovies.isVisible = false
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    movieAdapter.submitList(stateview.data)
                    binding.recyclerMovies.isVisible = true
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun searchMovies(query: String) {
        viewModel.searchMovies(query).observe(viewLifecycleOwner) { stateview ->
            when (stateview) {
                is StateView.Loading -> {
                    binding.recyclerMovies.isVisible = false
                    binding.progressBar.isVisible = true
                }

                is StateView.Success -> {
                    binding.progressBar.isVisible = false
                    movieAdapter.submitList(stateview.data)
                    binding.recyclerMovies.isVisible = true
                }

                is StateView.Error -> {
                    binding.progressBar.isVisible = false
                }
            }
        }
    }

    private fun initRecycler() {
        movieAdapter = MovieAdapter(
            context = requireContext(),
            layoutInflater = R.layout.movie_genre_item
        )

        with(binding.recyclerMovies) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }
    }

    private fun initSearchView() {
        binding.simpleSearchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                query.let {
                    searchMovies(query)
                    hideKeyboard()
                    return true
                }
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("SimpleSearchView", "Text changed:$newText")
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                Log.d("SimpleSearchView", "Text cleared")
                return false
            }
        })

        binding.simpleSearchView.setOnSearchViewListener(object : SimpleSearchView.SearchViewListener {
            override fun onSearchViewShown() {
                Log.d("SimpleSearchView", "onSearchViewShown")
            }

            override fun onSearchViewClosed() {
                getMoviesByGenre()
            }

            override fun onSearchViewShownAnimation() {
                Log.d("SimpleSearchView", "onSearchViewShownAnimation")
            }

            override fun onSearchViewClosedAnimation() {
                Log.d("SimpleSearchView", "onSearchViewClosedAnimation")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}