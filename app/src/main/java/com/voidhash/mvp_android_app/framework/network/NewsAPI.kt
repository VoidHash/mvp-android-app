package com.voidhash.mvp_android_app.framework.network

import com.voidhash.mvp_android_app.framework.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsAPI {

    //vase url = "https://newsapi.org"
    @GET("/v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") countryCode: String = "br",
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = "97d65032c20b41b0814dc898b2eab8c2"
    ): Response<NewsModel>

    @GET("/v2/everything")
    suspend fun searchNews(
        @Query("q") searchQuery: String,
        @Query("page") pageNumber: Int = 1,
        @Query("apiKey") apiKey: String = "97d65032c20b41b0814dc898b2eab8c2"
    ): Response<NewsModel>
}