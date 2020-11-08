package com.iwayriway.rfilmapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.iwayriway.rfilmapi.`interface`.MovieAPI
import com.iwayriway.rfilmapi.model.Movie
import com.iwayriway.rfilmapi.response.GetMovieResponse
import com.iwayriway.rfilmapi.utils.MoviesRespository
import com.iwayriway.rfilmapi.utils.Retro
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        MoviesRespository.getPopularMovies()
//        Log.e("dump", "MASUK")
        getMoviesApi()
    }

    fun getMoviesApi(){
        val retro = Retro().getRetroClientInstance().create(MovieAPI::class.java)
        retro.getMovies(page = 1).enqueue(object : Callback<GetMovieResponse>{
            override fun onResponse(
                call: Call<GetMovieResponse>,
                response: Response<GetMovieResponse>
            ) {
                if(response.isSuccessful){
                    val responseBody = response.body()
                    if(responseBody != null){
                        Log.e("dump", "Movies: ${responseBody.movies}")
                    } else {
                        Log.e("dump_err", "GAGAL BRO")
                    }
                } else {
                    Log.e("dump_err", "RESPON GA SUKSES")
                }
            }

            override fun onFailure(call: Call<GetMovieResponse>, t: Throwable) {
                Log.e("dump_err", t.message.toString())
            }

        })
    }
}