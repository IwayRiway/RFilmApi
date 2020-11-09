package com.iwayriway.rfilmapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.iwayriway.rfilmapi.`interface`.MovieAPI
import com.iwayriway.rfilmapi.model.Movie
import com.iwayriway.rfilmapi.response.GetMovieDetailResponse
import com.iwayriway.rfilmapi.utils.Retro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

//        val data = intent.getParcelableExtra<Movie>("data")
        getMovieDetail(299534)
    }

    fun getMovieDetail(movie_id : Int){
        val retro = Retro().getRetroClientInstance().create(MovieAPI::class.java)
        retro.getMovieDetail(movie_id = movie_id).enqueue(object : Callback<GetMovieDetailResponse>{
            override fun onResponse(call: Call<GetMovieDetailResponse>, response: Response<GetMovieDetailResponse>) {
                val response = response.body()
                Log.e("overview", response!!.overview)
            }

            override fun onFailure(call: Call<GetMovieDetailResponse>, t: Throwable) {
                Log.e("dump_err", t.message.toString())
            }

        })
    }
}