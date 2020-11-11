package com.iwayriway.rfilmapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iwayriway.rfilmapi.`interface`.MovieAPI
import com.iwayriway.rfilmapi.adapter.FilmAdapter
import com.iwayriway.rfilmapi.adapter.MovieAdapter
import com.iwayriway.rfilmapi.model.Movie
import com.iwayriway.rfilmapi.response.GetMovieResponse
import com.iwayriway.rfilmapi.utils.Retro
import kotlinx.android.synthetic.main.activity_film.*
import kotlinx.android.synthetic.main.activity_film.progressBar
import kotlinx.android.synthetic.main.activity_film.rv_movie
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmActivity : AppCompatActivity() {

    var dataList:MutableList<Movie> = ArrayList()
    var position:Int = 0
    private var page:Int = 1
    var loading:Boolean = false
    var ln = GridLayoutManager(this, 3)
    lateinit var movieAdapter: FilmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_film)

        rv_movie.layoutManager = ln
        getMoviesApi(page)

        rv_movie.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    var item = (rv_movie.layoutManager as GridLayoutManager).childCount
                    var list = (rv_movie.layoutManager as GridLayoutManager).findFirstCompletelyVisibleItemPosition()
                    var count = rv_movie.adapter!!.itemCount

                    if (!loading) {
                        if (item + list >= count) {
                            page = page + 1
                            getMoviesUpdateApi(page)
                        }
                    }

                }
            }
        })

    }

    fun getMoviesApi(page: Int){
        val retro = Retro().getRetroClientInstance().create(MovieAPI::class.java)
        retro.getMovies(page = page).enqueue(object : Callback<GetMovieResponse> {
            override fun onResponse(
                    call: Call<GetMovieResponse>,
                    response: Response<GetMovieResponse>
            ) {
                val responseBody = response.body()
                dataList = responseBody!!.movies
                position = dataList.size
                movieAdapter = FilmAdapter(this@FilmActivity)
                rv_movie.adapter = movieAdapter
                movieAdapter.setFilm(dataList)
            }

            override fun onFailure(call: Call<GetMovieResponse>, t: Throwable) {
                Log.e("dump_err", t.message.toString())
            }

        })
    }

    fun getMoviesUpdateApi(page: Int){
        loading = true
        progressBar.visibility = View.VISIBLE

        val retro = Retro().getRetroClientInstance().create(MovieAPI::class.java)
        retro.getMovies(page = page).enqueue(object : Callback<GetMovieResponse> {
            override fun onResponse(
                    call: Call<GetMovieResponse>,
                    response: Response<GetMovieResponse>
            ) {
                val responseBody = response.body()
                dataList.addAll(responseBody!!.movies)
//                for (i in responseBody!!.movies) {
//                    dataList.add(
//                            Movie(id = i.id, title = i.title, poster_path = i.poster_path)
//                    )
//                }

                Handler().postDelayed({
                    loading = false
                    progressBar.visibility = View.INVISIBLE
                    movieAdapter = FilmAdapter(this@FilmActivity)
                    rv_movie.adapter = movieAdapter
                    movieAdapter.setFilm(dataList)
                    rv_movie.scrollToPosition(position)
                    position = dataList.size
                }, 3000)
            }

            override fun onFailure(call: Call<GetMovieResponse>, t: Throwable) {
                Log.e("dump_err", t.message.toString())
            }

        })
    }
}