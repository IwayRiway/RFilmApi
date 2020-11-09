package com.iwayriway.rfilmapi

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.iwayriway.rfilmapi.`interface`.MovieAPI
import com.iwayriway.rfilmapi.adapter.MovieAdapter
import com.iwayriway.rfilmapi.model.Movie
import com.iwayriway.rfilmapi.response.GetMovieResponse
import com.iwayriway.rfilmapi.utils.Retro
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private  var dataList = ArrayList<Movie>()
    private var page:Int = 1
    var loading:Boolean = false
    private val recyclerViewState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rv_movie.layoutManager = GridLayoutManager(this, 3)
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
                rv_movie.adapter = MovieAdapter(dataList) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java).putExtra("data", it)
                    startActivity(intent)
                }
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
                for (i in responseBody!!.movies) {
                    dataList.add(
                            Movie(id = i.id, title = i.title, poster_path = i.poster_path)
                    )
                }

                Handler().postDelayed({
                    loading = false
                    progressBar.visibility = View.INVISIBLE
                    rv_movie.adapter = MovieAdapter(dataList) {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java).putExtra("data", it)
                        startActivity(intent)
                    }
                }, 3000)
            }

            override fun onFailure(call: Call<GetMovieResponse>, t: Throwable) {
                Log.e("dump_err", t.message.toString())
            }

        })
    }
}

