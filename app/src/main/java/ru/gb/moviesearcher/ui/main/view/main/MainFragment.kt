package ru.gb.moviesearcher.ui.main.view.main

import android.Manifest
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.main_fragment.*
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.databinding.MainFragmentBinding
import ru.gb.moviesearcher.ui.main.model.MoviesListDTO
import ru.gb.moviesearcher.ui.main.view.details.DetailFragment
import ru.gb.moviesearcher.ui.main.utils.hide
import ru.gb.moviesearcher.ui.main.utils.show
import ru.gb.moviesearcher.ui.main.utils.showSnackBar
import ru.gb.moviesearcher.ui.main.view.CHILD_MODE
import ru.gb.moviesearcher.ui.main.view.CHILD_MODE_KEY
import ru.gb.moviesearcher.ui.main.viewmodel.AppState
import ru.gb.moviesearcher.ui.main.viewmodel.MainViewModel
import java.io.IOException

private const val FIRST_PAGE = 1;

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }


    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!




    private val adapter: MainAdapter by lazy {
        MainAdapter()
    }
    private val adapterSecond: MainAdapter by lazy {
        MainAdapter()
    }

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



        adapter.listener = MainAdapter.OnItemViewClicksListener { movie -> showContent(movie) }

        adapterSecond.listener =
            MainAdapter.OnItemViewClicksListener { movie -> showContent(movie) }

        with(binding) {
            mainFragmentRecyclerView.adapter = adapter
            mainFragmentRecyclerView.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            mainFragmentRecyclerViewBottom.adapter = adapterSecond
            mainFragmentRecyclerViewBottom.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }



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

        viewModel.getMovieFromInternet(FIRST_PAGE)
    }

    private fun showContent(movie: MoviesListDTO.MovieList) {

        activity?.supportFragmentManager?.let { fragmentManager ->
            fragmentManager.beginTransaction()
                .replace(R.id.container, DetailFragment.newInstance(Bundle().apply {
                    putParcelable(DetailFragment.MOVIE_EXTRA, movie)
                }))
                .addToBackStack("")
                .commit()
        }

    }

    private fun renderData(appState: AppState, isNewMovies: Boolean) {
        val preferences: SharedPreferences = requireActivity().getSharedPreferences(CHILD_MODE,MODE_PRIVATE)
        val isChecked: Boolean = preferences.getBoolean(CHILD_MODE_KEY, false)

        when (appState) {
            is AppState.Loading -> binding.loadingLayout.show()
            is AppState.Success -> {
                binding.loadingLayout.hide()
                if (isNewMovies) {
                    adapter.setMovie(appState.movieDTO.results, isChecked)
                } else {
                    adapterSecond.setMovie(appState.movieDTO.results, isChecked)
                }
            }
            is AppState.Error -> {
                binding.loadingLayout.hide()
                binding.mainView.showSnackBar(
                    "Error ${appState.error}",
                    "Reload",
                    { viewModel.getMovieFromInternet(FIRST_PAGE) },
                )
            }
        }
    }





    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}