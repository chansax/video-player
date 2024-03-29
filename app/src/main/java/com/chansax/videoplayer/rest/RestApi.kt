package com.chansax.videoplayer.rest

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by chandan on 2019-09-04.
 * Implementation of Retrofit instance for network calls.
 */

object RestApi {
    private const val BASE_URL = "https://cmh-external-files.s3.amazonaws.com/"

    fun fetchVideos() = createRestApi().fetchVideo()

    private fun createRetrofitInstance() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun createRestApi() = createRetrofitInstance().create(VideoApi::class.java)
}

