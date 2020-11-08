package com.iwayriway.rfilmapi.utils

import android.util.Log
import com.iwayriway.rfilmapi.`interface`.MovieAPI
import com.iwayriway.rfilmapi.model.Movie
import com.iwayriway.rfilmapi.response.GetMovieResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MoviesRespository {
    private val api: MovieAPI

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(MovieAPI::class.java)
    }

    fun getPopularMovies(page: Int = 1, onSuccess: (movies: List<Movie>) -> Unit, onError: () -> Unit){
        api.getMovies(page = page).enqueue(object : Callback<GetMovieResponse>{
            override fun onResponse(call: Call<GetMovieResponse>, response: Response<GetMovieResponse>) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
//                        Log.e("dump", "Movies: ${responseBody.movies}")
                        onSuccess.invoke(responseBody.movies)
                    } else {
                        onError.invoke()
//                        Log.e("dump_err", "GAGAL BRO")
                    }
                } else {
                    onError.invoke()
//                    Log.e("dump_err", "GA SUKSES")
                }
            }

            override fun onFailure(call: Call<GetMovieResponse>, t: Throwable) {
                Log.e("dump_err", t.message.toString())
            }

        })
    }
}