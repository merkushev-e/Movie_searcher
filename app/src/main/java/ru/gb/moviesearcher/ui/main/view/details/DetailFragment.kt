package ru.gb.moviesearcher.ui.main.view.details


import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.api.load
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.databinding.DetailFragmentBinding
import ru.gb.moviesearcher.ui.main.model.*
import ru.gb.moviesearcher.ui.main.utils.showSnackBar
import ru.gb.moviesearcher.ui.main.viewmodel.AppState
import ru.gb.moviesearcher.ui.main.viewmodel.DetailsViewModel
import java.util.*


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
    private lateinit var movieList: MoviesListDTO.MovieList
    private var movieDTO: MovieDTO? = null
    private var movie: Movie? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        _binding = DetailFragmentBinding.bind(view)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieList = arguments?.getParcelable(MOVIE_EXTRA) ?: MoviesListDTO.MovieList()
        viewModel.getMovieFromRemoteSource(movieList)
        viewModel.liveData.observe(viewLifecycleOwner) { AppState ->
            renderData(AppState)
        }

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

                movieDTO = state.movieDTO


                with(binding) {
                    headerTitle.text = movieDTO?.title
                    moviesYear.text = movieDTO?.release_date.toString().substring(0, 4)
                    movieRatingCount.text = movieDTO?.vote_average.toString()
                    movieDescription.text = movieDTO?.overview
                    for (el in movieDTO?.genres!!) {
                        movieGenre.text = el.name.toString()
                    }
                }
                saveMovie(movieDTO)
                updateMovie(movieDTO)

            }

            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingLayout.visibility = View.GONE
                binding.mainView.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload),
                    { viewModel.getMovieFromRemoteSource(movieList) }
                    //            requestLink + "${id}?api_key=${BuildConfig.MOVIE_DB_API_KEY}"
                )
            }
        }

    }


    private fun updateCurrentMovie (movieDTO: MovieDTO?, note: String) {
        if (movieDTO != null) {
            viewModel.updateInDB(
                Movie(
                    movieDTO.id.toLong(),
                    movieDTO.title,
                    movieDTO.release_date,
                    movieDTO.vote_average,
                    note,
                    Date().time
                )
            )
        }

    }

    private fun updateMovie(movieDTO: MovieDTO?) {
        if (movieDTO != null) {
            viewModel.updateInDB2(
                Movie(
                    movieDTO.id.toLong(),
                    movieDTO.title,
                    movieDTO.release_date,
                    movieDTO.vote_average,
                    "",
                    Date().time
                )
            )
        }
    }




    private fun saveMovie(movieDTO: MovieDTO?) {
        if (movieDTO != null) {
            viewModel.saveMovieToDB(
                Movie(
                    movieDTO.id.toLong(),
                    movieDTO.title,
                    movieDTO.release_date,
                    movieDTO.vote_average,
                    "",
                    Date().time
                )
            )
        }
    }

    private fun getText() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        binding.buttonSaveNote.setOnClickListener {

            updateCurrentMovie(movieDTO, binding.editTextNotes.text.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}