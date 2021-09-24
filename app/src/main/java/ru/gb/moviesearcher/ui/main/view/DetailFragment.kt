package ru.gb.moviesearcher.ui.main.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.databinding.DetailFragmentBinding
import ru.gb.moviesearcher.ui.main.model.*

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
    private lateinit var movie: MoviesListDTO.MovieList

    private val localResultBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            Log.d("Debug", "onReceive message  ${Thread.currentThread()}")

            when (intent?.getStringExtra(RESULT_EXTRA)) {
                SUCCESS_RESULT -> {
                    intent.getParcelableExtra<MovieDTO>(MOVIE_DTO_EXTRA)?.let { movieDTO ->
                        displayMovie(movieDTO)
                    }
                }

                else -> {
                    binding.mainView.showSnackBar("Error", "Reload", { })
                }
            }

        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Debug", "Register Receiver  ${Thread.currentThread()}")

        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(localResultBroadcastReceiver, IntentFilter(DETAILS_INTENT_FILTER))

    }

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

        arguments?.getParcelable<MoviesListDTO.MovieList>(MOVIE_EXTRA).let {

            binding.loadingLayout.visibility = View.GONE
            binding.mainView.visibility = View.VISIBLE
            if (it != null) {
                getMovie(it.id)
            }
        }


//        arguments?.getParcelable<MoviesListDTO.MovieList>(MOVIE_EXTRA)?.let { movies ->

//            with(binding) {
//                loadingLayout.visibility = View.GONE
//                headerTitle.text = movies.movieName
//                moviesYear.text = movies.movieYear.toString()
//                movieRatingCount.text = movies.movieRate.toString()
//                movieDescription.text = movies.movieDescription
//                moviesImg.setImageResource(movies.moviePoster)

//                val moviesLoader = MoviesLoader(
//                    movies.id,
//                    object : MoviesLoader.MovieLoaderListener {
//                        override fun onLoaded(movieDTO: MovieDTO) {
//
//                            displayMovie(movieDTO)
//                        }
//
//                        override fun onFailed(throwable: Throwable) {
//                            requireActivity().runOnUiThread {
//                                Toast.makeText(
//                                    context,
//                                    throwable.localizedMessage,
//                                    Toast.LENGTH_SHORT
//                                )
//                                    .show()
//                            }
//                        }
//
//
//                    }
//                )
//                moviesLoader.goToInternet()
//            }
//
//        }
//
//    }

//        val movies = arguments?.getParcelable<Movie>(MOVIE_EXTRA)
//        if (movies != null){
//            binding.loadingLayout.visibility = View.GONE
//            binding.headerTitle.text = movies.movieName
//            binding.moviesYear.text = movies.movieYear.toString()
//            binding.movieRatingCount.text = movies.movieRate.toString()
//            binding.movieDescription.text = movies.movieDescription
//            binding.moviesImg.setImageResource(movies.moviePoster)
//        }
        }


        fun displayMovie(movie: MovieDTO) {

            binding.loadingLayout.visibility = View.GONE
            binding.mainView.visibility = View.VISIBLE
            with(binding) {
                headerTitle.text = movie.title
                moviesYear.text = movie.release_date.toString().substring(0, 4)
                movieRatingCount.text = movie.vote_average.toString()
                movieDescription.text = movie.overview
                for (el in movie.genres) {
                    movieGenre.text = el.name.toString()
                }
            }
        }


        override fun onDestroy() {
            super.onDestroy()
            _binding = null

            LocalBroadcastManager.getInstance(requireContext())
                .unregisterReceiver(localResultBroadcastReceiver)
        }


    private fun getMovie(id: Int) {

        binding.loadingLayout.visibility = View.VISIBLE
        binding.mainView.visibility = View.GONE
        Log.d("Debug", "Start service ${Thread.currentThread()}")

        requireActivity().startService(Intent(requireContext(), DetailsService::class.java).apply {
            putExtra(MOVIE_ID_EXTRA,id)
        })
    }
}