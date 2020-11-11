package com.iwayriway.rfilmapi.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.iwayriway.rfilmapi.DetailActivity
import com.iwayriway.rfilmapi.R
import com.iwayriway.rfilmapi.model.Movie

class FilmAdapter(val context: Context): RecyclerView.Adapter<FilmAdapter.FilmViewHolder>(){

    private val movies: MutableList<Movie> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val inflatedView:View = layoutInflater.inflate(R.layout.item_film, parent,false)
        return FilmViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: FilmViewHolder, position: Int) {
        holder.bindItem(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun setFilm(data:List<Movie>){
        movies.clear()
        movies.addAll(data)
        notifyDataSetChanged()
    }

    inner class FilmViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val tvTitle: TextView = view.findViewById(R.id.tv_judul)
        private val ivPoster : ImageView = view.findViewById(R.id.iv_poster)

        fun bindItem(m:Movie){
            tvTitle.text = m.title
            var poster = "https://image.tmdb.org/t/p/w342"+ m.poster_path

            Glide.with(context).load(poster).into(ivPoster)

            itemView.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("data", m.id)
                context.startActivity(intent)
            }
        }
    }

}