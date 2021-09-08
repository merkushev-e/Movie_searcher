package ru.gb.moviesearcher.ui.main.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.databinding.MainFragmentBinding
import ru.gb.moviesearcher.ui.main.viewmodel.AppState
import ru.gb.moviesearcher.ui.main.viewmodel.MainViewModel

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

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

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner,
            { appState -> renderData(appState) })

        viewModel.getMovieFromLocalSource()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Loading -> binding.loadingLayout.visibility = View.VISIBLE
            is AppState.Success -> {
//                binding.loadingLayout.visibility = View.GONE
//                binding.newMoviesFilmName1.text = appState.movies.movie.movieName
//                binding.newMoviesRatingCount.text = appState.movies.rating.toString()
//                binding.newMoviesYearItem1.text = appState.movies.year.toString()
            }
            is AppState.Error ->{
                binding.loadingLayout.visibility = View.GONE
                Snackbar
                    .make(binding.mainView, "Error ${appState.error}", Snackbar.LENGTH_INDEFINITE)
                    .setAction("Reload"){viewModel.getMovieFromLocalSource()}
                    .show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}