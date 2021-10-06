package ru.gb.moviesearcher.ui.main.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.history_fragment_item.view.*
import ru.gb.moviesearcher.R
import ru.gb.moviesearcher.ui.main.model.Movie
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.RecyclerItemViewHolder>() {

    private var data: List<Movie> = arrayListOf()

    fun setData(data: List<Movie>){
        this.data = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.history_fragment_item,parent,false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }
    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: Movie) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                with(itemView){
                    movie_name_history.text = data.movieName
                    release_date_history.text = data.releaseDate.substring(0, 4)
                    rate_history.text = data.rate.toString()
                    time_history.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(data.timestamp))
                    note_history.text = data.note
                }


            }

        }
    }

}
