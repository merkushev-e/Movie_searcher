package ru.gb.moviesearcher.ui.main.view


import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import ru.gb.moviesearcher.BuildConfig
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.databinding.DetailFragmentBinding
import ru.gb.moviesearcher.ui.main.model.*
import ru.gb.moviesearcher.ui.main.viewmodel.AppState
import ru.gb.moviesearcher.ui.main.viewmodel.DetailsViewModel




class DetailFragment : Fragment() {

    companion object {
        const val MOVIE_EXTRA = "Movie"
        fun newInstance(bundle: Bundle): DetailFragment = DetailFragment().apply {
            arguments = bundle
        }


    }

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var movie: MoviesListDTO.MovieList



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        _binding = DetailFragmentBinding.bind(view)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       movie = arguments?.getParcelable(MOVIE_EXTRA) ?: MoviesListDTO.MovieList()
        viewModel.liveData.observe(viewLifecycleOwner) { AppState ->
            renderData(AppState)
        }
        viewModel.getMovieFromRemoteSource(movie)
    }


    private fun renderData(state: AppState) {
        when (state) {
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE
                binding.mainView.visibility = View.GONE
            }
            is AppState.SuccessDetails -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.moviesImg.load("https://image.tmdb.org/t/p/w500/${state.movieDTO.poster_path}")
                val movieDTO = state.movieDTO

                with(binding) {
                    headerTitle.text = movieDTO.title
                    moviesYear.text = movieDTO.release_date.toString().substring(0, 4)
                    movieRatingCount.text = movieDTO.vote_average.toString()
                    movieDescription.text = movieDTO.overview
                    for (el in movieDTO.genres) {
                        movieGenre.text = el.name.toString()
                    }
                }
            }

            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    {viewModel.getMovieFromRemoteSource(movie)}
                    //            requestLink + "${id}?api_key=${BuildConfig.MOVIE_DB_API_KEY}"
                )
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}