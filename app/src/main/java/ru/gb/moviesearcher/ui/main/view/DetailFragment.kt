package ru.gb.moviesearcher.ui.main.view

import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.databinding.DetailFragmentBinding
import ru.gb.moviesearcher.ui.main.model.Movie
import ru.gb.moviesearcher.ui.main.model.MovieDTO
import ru.gb.moviesearcher.ui.main.model.MoviesLoader

class DetailFragment : Fragment() {

    companion object {
        const val MOVIE_EXTRA = "Movie"
//        fun newInstance(bundle: Bundle): DetailFragment{
//            val fragment = DetailFragment()
//            fragment.arguments = bundle
//            return fragment
//        }

        fun newInstance(bundle: Bundle): DetailFragment = DetailFragment().apply {
            arguments = bundle
        }


    }

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!

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


        arguments?.getParcelable<Movie>(MOVIE_EXTRA)?.let { movies ->
            with(binding) {
                loadingLayout.visibility = View.GONE
                moviesImg.setImageResource(movies.moviePoster)

                val moviesLoader = MoviesLoader(
                    movies.movieId,
                    object : MoviesLoader.MovieLoaderListener {
                        override fun onLoaded(movieDTO: MovieDTO) {
                            requireActivity().runOnUiThread {
                                displayMovie(movieDTO)
                            }
                        }

                        override fun onFailed(throwable: Throwable) {
                            requireActivity().runOnUiThread {
                                Toast.makeText(context, throwable.localizedMessage, Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }


                    }
                )
                moviesLoader.goToInternet()
            }

        }

    }

//        val movies = arguments?.getParcelable<Movie>(MOVIE_EXTRA)
//        if (movies != null){
//            binding.loadingLayout.visibility = View.GONE
//            binding.headerTitle.text = movies.movieName
//            binding.moviesYear.text = movies.movieYear.toString()
//            binding.movieRatingCount.text = movies.movieRate.toString()
//            binding.movieDescription.text = movies.movieDescription
//            binding.moviesImg.setImageResource(movies.moviePoster)
//        }
//    }


    fun displayMovie(movie: MovieDTO) {
        with(binding) {
            headerTitle.text = movie.original_title
            moviesYear.text = movie.release_date.toString()
            movieRatingCount.text = movie.vote_average.toString()
            movieDescription.text = movie.overview
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}