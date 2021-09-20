package ru.gb.moviesearcher.ui.main.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.ui.main.model.Movie
import ru.gb.moviesearcher.ui.main.model.Movies
import ru.gb.moviesearcher.ui.main.model.MoviesListDTO

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    var moviesData: List<MoviesListDTO.MovieList> = listOf()

    fun setMovie(movies: List<MoviesListDTO.MovieList>){
        moviesData = movies
        notifyDataSetChanged()
    }

    var listener: OnItemViewClicksListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.main_fragment_item, parent,false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(moviesData[position])
    }

    override fun getItemCount(): Int {
        return moviesData.size
    }

    inner class MainViewHolder(view: View) : RecyclerView.ViewHolder(view){
        fun bind(movie: MoviesListDTO.MovieList) {
            val movieNameShort = movie.title
            if (movieNameShort.length < 15) {
                itemView.findViewById<TextView>(R.id.new_movies_film_name).text = movieNameShort
            } else if (movieNameShort.length > 15){
                itemView.findViewById<TextView>(R.id.new_movies_film_name).text = movieNameShort.substring(0,13) + ".."
            }
            itemView.apply {
                findViewById<TextView>(R.id.new_movies_year_item).text = movie.release_date.substring(0,4)
                findViewById<TextView>(R.id.new_movies_rating_count).text = movie.vote_average.toString()
//                findViewById<ImageView>(R.id.new_movies_img_item).setImageResource(movie.moviePoster)
                setOnClickListener {
                    listener?.onItemClick(movie)
                }
            }

        }

    }

    fun interface OnItemViewClicksListener{
        fun onItemClick(movie: MoviesListDTO.MovieList)
    }


    fun removeListener() {
        listener = null
    }
}