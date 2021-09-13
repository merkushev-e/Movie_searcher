package ru.gb.moviesearcher.ui.main.view

import android.icu.lang.UCharacter
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.databinding.MainFragmentBinding
import ru.gb.moviesearcher.ui.main.model.Movie
import ru.gb.moviesearcher.ui.main.viewmodel.AppState
import ru.gb.moviesearcher.ui.main.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MainAdapter
    private lateinit var adapterSecond: MainAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.bind(view)
        return binding.root


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        adapter = MainAdapter()
        adapterSecond = MainAdapter()

        adapter.listener = MainAdapter.OnItemViewClicksListener { movie -> showContent(movie) }

        adapterSecond.listener = MainAdapter.OnItemViewClicksListener { movie -> showContent(movie)  }

        binding.mainFragmentRecyclerView.adapter = adapter
        binding.mainFragmentRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        binding.mainFragmentRecyclerViewBottom.adapter = adapterSecond
        binding.mainFragmentRecyclerViewBottom.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)


        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.liveDataNewMovies.observe(viewLifecycleOwner,
            { appState ->
                val isNewMovies = true
                renderData(appState, isNewMovies)
            })


        viewModel.liveDataPopularMovies.observe(viewLifecycleOwner,
            { appState ->
                val isNewMovies = false
                renderData(appState, isNewMovies)
            })

        viewModel.getMovieFromLocalSource()
    }

    private fun showContent(movie: Movie) {
        val fragmentManager = activity?.supportFragmentManager
        if (fragmentManager != null) {
            val bundle = Bundle()
            bundle.putParcelable(DetailFragment.MOVIE_EXTRA, movie)
            fragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment.newInstance(bundle))
                .addToBackStack("")
                .commit()
        }
    }

    private fun renderData(appState: AppState, isNewMovies: Boolean) {
        when (appState) {
            is AppState.Loading -> binding.loadingLayout.visibility = View.VISIBLE
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE
                if (isNewMovies) {
                    adapter.setMovie(appState.movies)
                } else {
                    adapterSecond.setMovie(appState.movies)
                }
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainView, "Error ${appState.error}", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload") { viewModel.getMovieFromLocalSource() }
                    .show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}