package com.iwayriway.rfilmapi

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils.isEmpty
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.iwayriway.rfilmapi.`interface`.MovieAPI
import com.iwayriway.rfilmapi.model.Movie
import com.iwayriway.rfilmapi.model.Rate
import com.iwayriway.rfilmapi.response.GetMovieDetailResponse
import com.iwayriway.rfilmapi.response.GetPostRateResponse
import com.iwayriway.rfilmapi.utils.Retro
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.rate_movie.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val data = intent.getParcelableExtra<Movie>("data")

        getMovieDetail(data!!.id!!)
        tv_movie_id.text = data.id.toString()

        btn_ubah.setOnClickListener {
            ubahRate("Ubah Rating " + data.title, tv_movie_id.text as String)
        }
    }

    private fun ubahRate(s: String, movie_id: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.rate_movie)
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        val tvDesc = dialog.findViewById(R.id.tv_judul_dialog) as TextView
        val etMovieId = dialog.findViewById(R.id.et_movie_id) as TextView
        tvDesc.text = s
        etMovieId.setText(movie_id)

        val btnClose = dialog.findViewById(R.id.btn_cancel) as Button
        val btnYes = dialog.findViewById(R.id.btn_save) as Button

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnYes.setOnClickListener {
            dialog.dismiss()
            if(isEmpty(dialog.et_rating.toString())){
                Log.e("rating", "die")
                Toast.makeText(this@DetailActivity, "kosong", Toast.LENGTH_LONG).show()
            } else {
                val query = dialog.et_rating.text.toString().toDouble()
//                var double: Double
//                double= query.toDouble()
                postRate(query, etMovieId.text.toString().toInt())
            }
        }
        dialog.show()
    }

    private fun postRate(query: Double, text: Int) {
        var req = Rate()
        req.value = query

        val retro = Retro().getRetroClientInstance().create(MovieAPI::class.java)
        retro.postRate(reqBody = req, movie_id = text).enqueue(object : Callback<GetPostRateResponse> {
            override fun onResponse(call: Call<GetPostRateResponse>, response: Response<GetPostRateResponse>) {
                val response = response.body()
                Log.e("respon", response.toString())
                if (response!!.success) {
                    tv_rate.text = query.toString()
                    Toast.makeText(this@DetailActivity, "" + response.status_message, Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@DetailActivity, "" + response.status_message, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<GetPostRateResponse>, t: Throwable) {
                Log.e("dump_err", t.message.toString())
            }

        })
    }

    fun getMovieDetail(movie_id: Int){
        val retro = Retro().getRetroClientInstance().create(MovieAPI::class.java)
        retro.getMovieDetail(movie_id = movie_id).enqueue(object : Callback<GetMovieDetailResponse> {
            override fun onResponse(call: Call<GetMovieDetailResponse>, response: Response<GetMovieDetailResponse>) {
                val response = response.body()
                Log.e("dump", response!!.title)
                tv_judul_detail.text = response!!.title
                tv_tagline.text = response.tagline
                tv_rate.text = response.vote_average.toString()
                tv_overview.text = response.overview

                Glide.with(this@DetailActivity)
                        .load("https://image.tmdb.org/t/p/original" + response.backdrop_path)
                        .into(imageView2)

                Glide.with(this@DetailActivity)
                        .load("https://image.tmdb.org/t/p/w342" + response.poster_path)
                        .into(imageView3)
            }

            override fun onFailure(call: Call<GetMovieDetailResponse>, t: Throwable) {
                Log.e("dump_err", t.message.toString())
            }

        })
    }
}