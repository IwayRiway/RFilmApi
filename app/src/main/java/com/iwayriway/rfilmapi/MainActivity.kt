package com.iwayriway.rfilmapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iwayriway.rfilmapi.`interface`.MovieAPI
import com.iwayriway.rfilmapi.adapter.MovieAdapter
import com.iwayriway.rfilmapi.model.Movie
import com.iwayriway.rfilmapi.response.GetMovieResponse
import com.iwayriway.rfilmapi.utils.MoviesRespository
import com.iwayriway.rfilmapi.utils.Retro
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private  var dataList = ArrayList<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_movie.layoutManager = LinearLayoutManager(this.applicationContext)
        getMoviesApi(1)
    }


    fun getMoviesApi(page:Int){
        val retro = Retro().getRetroClientInstance().create(MovieAPI::class.java)
        retro.getMovies(page = page).enqueue(object : Callback<GetMovieResponse>{
            override fun onResponse(
                call: Call<GetMovieResponse>,
                response: Response<GetMovieResponse>
            ) {
                val responseBody = response.body()
                for (i in responseBody!!.movies){
                    dataList.add(
                        Movie(
                            id = i.id,
                            title = i.title,
                            poster_path = i.poster_path
                        )
                    )
                }
                rv_movie.adapter = MovieAdapter(dataList){
                    val intent = Intent(this@MainActivity, DetailActivity::class.java).putExtra("data",it)
                    startActivity(intent)
                }
                Log.e("dump", dataList.toString())
            }

            override fun onFailure(call: Call<GetMovieResponse>, t: Throwable) {
                Log.e("dump_err", t.message.toString())
            }

        })
    }
}
//
//    fun getMoviesApi(page:Int){
//        val retro = Retro().getRetroClientInstance().create(MovieAPI::class.java)
//        retro.getMovies(page = page).enqueue(object : Callback<GetMovieResponse>{
//            override fun onResponse(
//                call: Call<GetMovieResponse>,
//                response: Response<GetMovieResponse>
//            ) {
//                if(response.isSuccessful){
//                    val responseBody = response.body()
//                    if(responseBody != null){
//                        for (i in responseBody.movies){
//                            dataList.add(
//                                Movie(
//                                    id = 1,
//                                    title = "Avengers",
//                                    poster_path = "",
//                                )
//                            )
////                            Log.e("dumpId", i.id.toString())
//                            dataList.add(
//                                Movie(
//                                    id = i.id,
//                                    title = i.title.toString(),
//                                    poster_path = i.poster_path.toString(),
//                                )
//                            )
//                        }
//                        Log.e("dump", dataList.toString())
//                    } else {
//                        Log.e("dump_err", "GAGAL BRO")
//                    }
//                } else {
//                    Log.e("dump_err", "RESPON GA SUKSES")
//                }
//            }
//
//            override fun onFailure(call: Call<GetMovieResponse>, t: Throwable) {
//                Log.e("dump_err", t.message.toString())
//            }
//
//        })
//    }
//}


