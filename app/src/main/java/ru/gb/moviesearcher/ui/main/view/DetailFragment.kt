package ru.gb.moviesearcher.ui.main.view

import android.os.Bundle
import android.view.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.databinding.DetailFragmentBinding
import ru.gb.moviesearcher.databinding.MainFragmentBinding
import ru.gb.moviesearcher.ui.main.model.Movie
import ru.gb.moviesearcher.ui.main.viewmodel.AppState
import ru.gb.moviesearcher.ui.main.viewmodel.MainViewModel

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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        arguments?.getParcelable<Movie>(MOVIE_EXTRA)?.let { movies ->
            with(binding) {
                loadingLayout.visibility = View.GONE
                headerTitle.text = movies.movieName
                moviesYear.text = movies.movieYear.toString()
                movieRatingCount.text = movies.movieRate.toString()
                movieDescription.text = movies.movieDescription
                moviesImg.setImageResource(movies.moviePoster)
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


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}