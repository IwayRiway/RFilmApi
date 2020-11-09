package com.iwayriway.rfilmapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.iwayriway.rfilmapi.`interface`.MovieAPI
import com.iwayriway.rfilmapi.model.Movie
import com.iwayriway.rfilmapi.response.GetMovieDetailResponse
import com.iwayriway.rfilmapi.utils.Retro
import kotlinx.android.synthetic.main.activity_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<Movie>("data")

        getMovieDetail(data!!.id!!)
    }

    fun getMovieDetail(movie_id : Int){
        val retro = Retro().getRetroClientInstance().create(MovieAPI::class.java)
        retro.getMovieDetail(movie_id = movie_id).enqueue(object : Callback<GetMovieDetailResponse>{
            override fun onResponse(call: Call<GetMovieDetailResponse>, response: Response<GetMovieDetailResponse>) {
                val response = response.body()
                Log.e("dump", response!!.title)
                tv_judul_detail.text = response!!.title
                tv_tagline.text = response.tagline
                tv_rate.text = response.vote_average.toString()
                tv_overview.text = response.overview

                Glide.with(this@DetailActivity)
                        .load("https://image.tmdb.org/t/p/original"+response.backdrop_path)
                        .into(imageView2)

                Glide.with(this@DetailActivity)
                        .load("https://image.tmdb.org/t/p/w342"+response.poster_path)
                        .into(imageView3)
            }

            override fun onFailure(call: Call<GetMovieDetailResponse>, t: Throwable) {
                Log.e("dump_err", t.message.toString())
            }

        })
    }
}