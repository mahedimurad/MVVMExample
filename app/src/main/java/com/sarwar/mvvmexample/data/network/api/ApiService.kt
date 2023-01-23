package com.sarwar.mvvmexample.data.network.api

import com.sarwar.mvvmexample.data.network.model.UnsplashApiResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // MAIN THREAD/ UI THREAD

    @GET("/search/photos")
    suspend fun searchImage(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") size: Int
    ) : Response<UnsplashApiResponse>

}