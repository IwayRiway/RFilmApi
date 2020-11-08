package com.iwayriway.rfilmapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iwayriway.rfilmapi.R
import com.iwayriway.rfilmapi.model.Movie

class MovieAdapter (private var data:List<Movie>, private val listener: (Movie) -> Unit)
    :RecyclerView.Adapter<MovieAdapter.MovieViewHolder>(){

    lateinit var contextAdapter : Context

    class MovieViewHolder(view:View) : RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_judul)
        private val ivPoster : ImageView = view.findViewById(R.id.iv_poster)

        fun bindItem(data:Movie, listener: (Movie) -> Unit, context: Context, position: Int){
            tvTitle.text = data.title
            var poster = "https://image.tmdb.org/t/p/w342"+ data.poster_path

            Glide.with(context).load(poster).into(ivPoster)

            itemView.setOnClickListener {
                listener(data)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        contextAdapter = parent.context

        val inflatedView:View = layoutInflater.inflate(R.layout.item_film, parent,false)
        return MovieViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindItem(data[position], listener, contextAdapter, position)
    }

    override fun getItemCount(): Int = data.size

}