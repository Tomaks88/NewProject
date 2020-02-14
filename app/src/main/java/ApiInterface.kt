package com.example.myapplication

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api/")
    fun getImages(@Query("key") key: String) : Call<Hits>

    @GET("api/")
    fun getImage(@Query("key") key: String, @Query("id") id: Int) : Call<Hits>

    @GET("api/")
    fun getImageSearch(@Query("key") key: String, @Query("q") query: String) : Call<Hits>

    companion object {

        var baseURL = "https://pixabay.com/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseURL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}