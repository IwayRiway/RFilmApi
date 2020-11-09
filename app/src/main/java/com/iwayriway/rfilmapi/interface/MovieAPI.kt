package com.iwayriway.rfilmapi.`interface`

import com.iwayriway.rfilmapi.model.Movie
import com.iwayriway.rfilmapi.response.GetMovieDetailResponse
import com.iwayriway.rfilmapi.response.GetMovieResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieAPI {
//    @GET("popular?api_key=4381b6c6e937d0c802d06d53cc1d9810")
//    fun getMovies() : Call<ResponseBody>

    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey:String = "4381b6c6e937d0c802d06d53cc1d9810",
        @Query("page") page:Int = 1,
        @Query("include_adult") include_adult:Boolean = false,
        @Query("query") query:String
    ): Call<GetMovieResponse>

    @GET("movie/popular")
    fun getMovies(
        @Query("api_key") apiKey:String = "4381b6c6e937d0c802d06d53cc1d9810",
        @Query("page") page:Int
    ): Call<GetMovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movie_id:Int,
        @Query("api_key") apiKey:String = "4381b6c6e937d0c802d06d53cc1d9810"
    ): Call<GetMovieDetailResponse>
}